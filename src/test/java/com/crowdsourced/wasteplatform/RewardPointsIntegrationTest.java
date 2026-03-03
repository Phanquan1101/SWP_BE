package com.crowdsourced.wasteplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crowdsourced.wasteplatform.entity.TxType;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.WasteCapability;
import com.crowdsourced.wasteplatform.repository.PointTransactionRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.WasteCapabilityRepository;
import com.crowdsourced.wasteplatform.service.reward.PointAwardService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(classes = com.crowdsourced.wasteplatform.WastePlatformApplication.class)
@AutoConfigureMockMvc
class RewardPointsIntegrationTest {

    private static final UUID AREA_HCM_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID CATEGORY_PLASTIC_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WasteCapabilityRepository wasteCapabilityRepository;

    @Autowired
    private PointTransactionRepository pointTransactionRepository;

    @Autowired
    private PointAwardService pointAwardService;

    private String adminToken;
    private String citizenToken;
    private String enterpriseToken;
    private String collectorToken;
    private UUID collectorId;

    @BeforeEach
    void setup() throws Exception {
        if (adminToken == null) {
            adminToken = loginAndGetAccessToken("admin@example.com", "Password@123");
            citizenToken = loginAndGetAccessToken("citizen@example.com", "Password@123");
            enterpriseToken = loginAndGetAccessToken("enterprise@example.com", "Password@123");
            collectorToken = loginAndGetAccessToken("collector@example.com", "Password@123");
            collectorId = userRepository.findByEmail("collector@example.com").map(User::getId).orElseThrow();
        }

        User collector = userRepository.findById(collectorId).orElseThrow();
        collector.setAreaId(AREA_HCM_ID);
        userRepository.save(collector);

        WasteCapability capability = wasteCapabilityRepository.findByWasteCategoryId(CATEGORY_PLASTIC_ID)
            .orElseGet(() -> WasteCapability.builder()
                .wasteCategoryId(CATEGORY_PLASTIC_ID)
                .dailyCapacityKg(new BigDecimal("1000.000"))
                .accepting(true)
                .build());
        capability.setAccepting(true);
        wasteCapabilityRepository.save(capability);
    }

    @Test
    void collectedStatusCreatesExactlyOneEarnTransaction() throws Exception {
        String reportId = prepareCollectedReport();
        UUID reportUuid = UUID.fromString(reportId);

        assertThat(pointTransactionRepository.countByReportIdAndTxType(reportUuid, TxType.EARN)).isEqualTo(1);
        assertThat(pointTransactionRepository.findByReportIdAndTxType(reportUuid, TxType.EARN))
            .isPresent()
            .get()
            .extracting(tx -> tx.getPoints())
            .isEqualTo(12);
    }

    @Test
    void reAwardCallDoesNotCreateDuplicateTransaction() throws Exception {
        String reportId = prepareCollectedReport();
        UUID reportUuid = UUID.fromString(reportId);

        pointAwardService.awardPointsForReport(reportId, collectorId.toString());
        pointAwardService.awardPointsForReport(reportId, collectorId.toString());

        assertThat(pointTransactionRepository.countByReportIdAndTxType(reportUuid, TxType.EARN)).isEqualTo(1);
    }

    @Test
    void citizenPointHistoryContainsEarnTransaction() throws Exception {
        String reportId = prepareCollectedReport();

        MvcResult result = mockMvc.perform(get("/citizen/points/transactions?page=0&size=20")
                .header("Authorization", "Bearer " + citizenToken))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        boolean found = false;
        for (JsonNode item : root.get("data").get("content")) {
            if (reportId.equals(item.get("reportId").asText())) {
                found = true;
                break;
            }
        }
        assertThat(found).isTrue();
    }

    private String prepareCollectedReport() throws Exception {
        upsertRewardRule();
        String reportId = createCitizenReport();
        acceptReport(reportId);
        assignCollector(reportId);
        String assignmentId = findAssignmentIdForReport(reportId);
        updateCollectorStatus(assignmentId, "ON_THE_WAY");
        updateCollectorStatus(assignmentId, "COLLECTED");
        return reportId;
    }

    private void upsertRewardRule() throws Exception {
        String body = """
            {
              "pointsPerKg": 3.5,
              "bonusQualityPoints": 2,
              "bonusFastCompletePoints": 1
            }
            """;
        mockMvc.perform(put("/admin/reward-rules/" + CATEGORY_PLASTIC_ID)
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk());
    }

    private String createCitizenReport() throws Exception {
        String payload = objectMapper.writeValueAsString(new CreatePayload());
        MvcResult result = mockMvc.perform(post("/citizen/reports")
                .header("Authorization", "Bearer " + citizenToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return node.get("data").get("id").asText();
    }

    private void acceptReport(String reportId) throws Exception {
        mockMvc.perform(post("/enterprise/reports/" + reportId + "/accept")
                .header("Authorization", "Bearer " + enterpriseToken))
            .andExpect(status().isOk());
    }

    private void assignCollector(String reportId) throws Exception {
        String payload = """
            {"collectorId":"%s"}
            """.formatted(collectorId);
        mockMvc.perform(post("/enterprise/reports/" + reportId + "/assign")
                .header("Authorization", "Bearer " + enterpriseToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk());
    }

    private String findAssignmentIdForReport(String reportId) throws Exception {
        MvcResult result = mockMvc.perform(get("/collector/assignments?status=ASSIGNED&page=0&size=50")
                .header("Authorization", "Bearer " + collectorToken))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        for (JsonNode item : node.get("data").get("content")) {
            if (reportId.equals(item.get("reportId").asText())) {
                return item.get("assignmentId").asText();
            }
        }
        throw new IllegalStateException("Cannot find assignment for report " + reportId);
    }

    private void updateCollectorStatus(String assignmentId, String statusValue) throws Exception {
        String payload = """
            {
              "status": "%s",
              "note": "reward-test",
              "lastKnownLatitude": 10.762622,
              "lastKnownLongitude": 106.660172
            }
            """.formatted(statusValue);
        mockMvc.perform(patch("/collector/assignments/" + assignmentId + "/status")
                .header("Authorization", "Bearer " + collectorToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk());
    }

    private String loginAndGetAccessToken(String identifier, String password) throws Exception {
        String payload = """
            {"identifier":"%s","password":"%s"}
            """.formatted(identifier, password);
        MvcResult result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return node.get("data").get("tokens").get("accessToken").asText();
    }

    static class CreatePayload {
        public String areaId = AREA_HCM_ID.toString();
        public String wasteCategoryId = CATEGORY_PLASTIC_ID.toString();
        public String description = "Reward points test";
        public BigDecimal estimatedWeightKg = new BigDecimal("2.75");
        public BigDecimal latitude = new BigDecimal("10.762622");
        public BigDecimal longitude = new BigDecimal("106.660172");
        public String addressText = "Reward test address";
        public List<String> imageUrls = List.of("https://example.com/reward-test.jpg");
    }
}
