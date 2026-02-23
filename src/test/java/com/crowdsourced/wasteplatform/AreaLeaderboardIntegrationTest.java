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
class AreaLeaderboardIntegrationTest {

    private static final UUID ROOT_HCM_AREA_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID CATEGORY_PLASTIC_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WasteCapabilityRepository wasteCapabilityRepository;

    private String adminToken;
    private String managerToken;
    private String collectorToken;
    private UUID collectorId;

    @BeforeEach
    void setup() throws Exception {
        if (adminToken == null) {
            adminToken = loginAndGetAccessToken("admin@example.com", "Password@123");
            managerToken = loginAndGetAccessToken("enterprise@example.com", "Password@123");
            collectorToken = loginAndGetAccessToken("collector@example.com", "Password@123");
            collectorId = userRepository.findByEmail("collector@example.com")
                .map(User::getId)
                .orElseThrow();
        }

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
    void leaderboardReturnsSortedUsersWithRank() throws Exception {
        String areaId = createIsolatedArea();
        setCollectorArea(areaId);
        upsertRewardRule();

        RegisteredCitizen topCitizen = registerCitizen("leader-top", "Leader Top");
        RegisteredCitizen secondCitizen = registerCitizen("leader-second", "Leader Second");

        String reportTop = createReport(topCitizen.accessToken(), areaId, new BigDecimal("9.2"));
        completeReport(reportTop);

        String reportSecond = createReport(secondCitizen.accessToken(), areaId, new BigDecimal("3.6"));
        completeReport(reportSecond);

        MvcResult leaderboardResult = mockMvc.perform(get("/areas/" + areaId + "/leaderboard?days=30&limit=10"))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode root = objectMapper.readTree(leaderboardResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JsonNode items = root.path("data").path("items");

        JsonNode topRow = findRow(items, topCitizen.userId());
        JsonNode secondRow = findRow(items, secondCitizen.userId());

        assertThat(topRow).isNotNull();
        assertThat(secondRow).isNotNull();
        assertThat(topRow.get("totalPoints").asLong()).isEqualTo(9);
        assertThat(secondRow.get("totalPoints").asLong()).isEqualTo(3);
        assertThat(topRow.get("rank").asInt()).isLessThan(secondRow.get("rank").asInt());
    }

    private String createIsolatedArea() throws Exception {
        String requestBody = """
            {
              "parentId": "%s",
              "name": "Leaderboard Test Area %s"
            }
            """.formatted(ROOT_HCM_AREA_ID, UUID.randomUUID());

        MvcResult result = mockMvc.perform(post("/admin/areas")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return node.path("data").path("id").asText();
    }

    private void setCollectorArea(String areaId) {
        User collector = userRepository.findById(collectorId).orElseThrow();
        collector.setAreaId(UUID.fromString(areaId));
        userRepository.save(collector);
    }

    private void upsertRewardRule() throws Exception {
        String body = """
            {
              "pointsPerKg": 1.0,
              "bonusQualityPoints": 0,
              "bonusFastCompletePoints": 0
            }
            """;
        mockMvc.perform(put("/admin/reward-rules/" + CATEGORY_PLASTIC_ID)
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk());
    }

    private RegisteredCitizen registerCitizen(String emailPrefix, String fullName) throws Exception {
        String email = emailPrefix + "." + UUID.randomUUID() + "@example.com";
        String payload = """
            {
              "email": "%s",
              "password": "Password@123",
              "fullName": "%s"
            }
            """.formatted(email, fullName);

        MvcResult result = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return new RegisteredCitizen(
            node.path("data").path("user").path("id").asText(),
            node.path("data").path("tokens").path("accessToken").asText()
        );
    }

    private String createReport(String citizenToken, String areaId, BigDecimal weight) throws Exception {
        String payload = objectMapper.writeValueAsString(new ReportPayload(areaId, weight));
        MvcResult result = mockMvc.perform(post("/citizen/reports")
                .header("Authorization", "Bearer " + citizenToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return node.path("data").path("id").asText();
    }

    private void completeReport(String reportId) throws Exception {
        acceptReport(reportId);
        assignCollector(reportId);
        String assignmentId = findAssignmentId(reportId);
        updateCollectorStatus(assignmentId, "ON_THE_WAY");
        updateCollectorStatus(assignmentId, "COLLECTED");
    }

    private void acceptReport(String reportId) throws Exception {
        mockMvc.perform(post("/enterprise/reports/" + reportId + "/accept")
                .header("Authorization", "Bearer " + managerToken))
            .andExpect(status().isOk());
    }

    private void assignCollector(String reportId) throws Exception {
        String payload = """
            {"collectorId":"%s"}
            """.formatted(collectorId);
        mockMvc.perform(post("/enterprise/reports/" + reportId + "/assign")
                .header("Authorization", "Bearer " + managerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk());
    }

    private String findAssignmentId(String reportId) throws Exception {
        MvcResult result = mockMvc.perform(get("/collector/assignments?status=ASSIGNED&page=0&size=50")
                .header("Authorization", "Bearer " + collectorToken))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        for (JsonNode item : node.path("data").path("content")) {
            if (reportId.equals(item.path("reportId").asText())) {
                return item.path("assignmentId").asText();
            }
        }
        throw new IllegalStateException("Cannot find assignment for report " + reportId);
    }

    private void updateCollectorStatus(String assignmentId, String statusValue) throws Exception {
        String payload = """
            {
              "status": "%s",
              "note": "leaderboard-test",
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

    private JsonNode findRow(JsonNode items, String userId) {
        for (JsonNode item : items) {
            if (userId.equals(item.path("userId").asText())) {
                return item;
            }
        }
        return null;
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
        return node.path("data").path("tokens").path("accessToken").asText();
    }

    private record RegisteredCitizen(String userId, String accessToken) {
    }

    static class ReportPayload {
        public String areaId;
        public String wasteCategoryId = CATEGORY_PLASTIC_ID.toString();
        public String description = "Leaderboard test report";
        public BigDecimal estimatedWeightKg;
        public BigDecimal latitude = new BigDecimal("10.762622");
        public BigDecimal longitude = new BigDecimal("106.660172");
        public String addressText = "Leaderboard test address";
        public List<String> imageUrls = List.of("https://example.com/leaderboard-test.jpg");

        ReportPayload(String areaId, BigDecimal estimatedWeightKg) {
            this.areaId = areaId;
            this.estimatedWeightKg = estimatedWeightKg;
        }
    }
}
