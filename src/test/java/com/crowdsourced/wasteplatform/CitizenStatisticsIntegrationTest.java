package com.crowdsourced.wasteplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.WasteCapability;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.WasteCapabilityRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;
import java.time.ZoneOffset;
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
class CitizenStatisticsIntegrationTest {

    private static final UUID AREA_HCM_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID CATEGORY_PLASTIC_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WasteCapabilityRepository wasteCapabilityRepository;

    @Autowired
    private UserRepository userRepository;

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

        upsertRewardRule();
    }

    @Test
    void citizenOverview_shouldShowReportsAndRedeemedVouchers() throws Exception {
        String monthParam = YearMonth.now(ZoneOffset.UTC).toString();
        JsonNode before = getCitizenOverview(monthParam);
        long beforeReports = before.get("reportsSentThisMonth").asLong();
        long beforeRedeems = before.get("totalVoucherRedemptions").asLong();

        String reportId = createCitizenReport();
        acceptReport(reportId);
        assignCollector(reportId);
        String assignmentId = findAssignmentIdForReport(reportId);
        updateCollectorStatus(assignmentId, "ON_THE_WAY");
        updateCollectorStatus(assignmentId, "COLLECTED");

        String voucherId = createEnterpriseVoucher();
        String redeemCode = redeemVoucher(voucherId);

        JsonNode after = getCitizenOverview(monthParam);
        assertThat(after.get("reportsSentThisMonth").asLong()).isGreaterThanOrEqualTo(beforeReports + 1);
        assertThat(after.get("totalVoucherRedemptions").asLong()).isGreaterThanOrEqualTo(beforeRedeems + 1);

        boolean foundRedeem = false;
        for (JsonNode item : after.get("redeemedVouchers")) {
            if (redeemCode.equals(item.get("redeemCode").asText())) {
                foundRedeem = true;
                break;
            }
        }
        assertThat(foundRedeem).isTrue();
    }

    private JsonNode getCitizenOverview(String monthParam) throws Exception {
        MvcResult result = mockMvc.perform(get("/citizen/statistics/overview?month=" + monthParam)
                .header("Authorization", "Bearer " + citizenToken))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return root.get("data");
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
        throw new IllegalStateException("Assignment not found for report: " + reportId);
    }

    private void updateCollectorStatus(String assignmentId, String statusValue) throws Exception {
        String payload = """
            {
              "status": "%s",
              "note": "stats-citizen-test",
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

    private String createEnterpriseVoucher() throws Exception {
        String payload = """
            {
              "code": "STAT-CITIZEN-%s",
              "title": "Citizen Stats Voucher",
              "description": "voucher for citizen statistics integration test",
              "pointsCost": 1,
              "stock": 20,
              "availableFrom": "2024-01-01T00:00:00Z",
              "availableTo": "2035-12-31T23:59:59Z",
              "isActive": true,
              "imageUrl": "https://example.com/voucher-citizen-stats.png"
            }
            """.formatted(UUID.randomUUID().toString().substring(0, 8));
        MvcResult result = mockMvc.perform(post("/enterprise/vouchers")
                .header("Authorization", "Bearer " + enterpriseToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return node.get("data").get("id").asText();
    }

    private String redeemVoucher(String voucherId) throws Exception {
        MvcResult result = mockMvc.perform(post("/citizen/vouchers/" + voucherId + "/redeem")
                .header("Authorization", "Bearer " + citizenToken))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return node.get("data").get("redeemCode").asText();
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
        public String description = "Citizen statistics test";
        public BigDecimal estimatedWeightKg = new BigDecimal("2.500");
        public BigDecimal latitude = new BigDecimal("10.762622");
        public BigDecimal longitude = new BigDecimal("106.660172");
        public String addressText = "Stats citizen";
        public List<String> imageUrls = List.of("https://example.com/stats-citizen.jpg");
    }
}
