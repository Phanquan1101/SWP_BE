package com.crowdsourced.wasteplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.repository.UserRepository;
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
class AdminStatisticsIntegrationTest {

    private static final UUID AREA_HCM_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID CATEGORY_PLASTIC_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private String adminToken;
    private String citizenToken;

    @BeforeEach
    void setup() throws Exception {
        if (adminToken == null) {
            adminToken = loginAndGetAccessToken("admin@example.com", "Password@123");
            citizenToken = loginAndGetAccessToken("citizen@example.com", "Password@123");
        }

        User collector = userRepository.findByEmail("collector@example.com").orElseThrow();
        collector.setAreaId(AREA_HCM_ID);
        userRepository.save(collector);
    }

    @Test
    void adminStatisticsEndpoints_shouldReturnValidAggregates() throws Exception {
        createCitizenReport();

        JsonNode overview = getOverview();
        assertThat(overview.get("totalActiveCollectors").asLong()).isGreaterThanOrEqualTo(1);
        assertThat(overview.get("totalActiveWasteCategories").asLong()).isGreaterThanOrEqualTo(1);
        assertThat(overview.get("totalActiveAreas").asLong()).isGreaterThanOrEqualTo(1);
        assertThat(overview.get("totalAcceptingWasteCategories").asLong()).isGreaterThanOrEqualTo(0);
        assertThat(overview.get("totalComplaintsReceived").asLong()).isGreaterThanOrEqualTo(0);
        assertThat(overview.get("totalResolvedComplaints").asLong()).isGreaterThanOrEqualTo(0);

        JsonNode collectorsByArea = getCollectorsByArea();
        boolean foundArea = false;
        for (JsonNode item : collectorsByArea) {
            if (AREA_HCM_ID.toString().equals(item.get("areaId").asText())) {
                assertThat(item.get("collectorCount").asLong()).isGreaterThanOrEqualTo(1);
                foundArea = true;
                break;
            }
        }
        assertThat(foundArea).isTrue();

        int year = YearMonth.now(ZoneOffset.UTC).getYear();
        String monthKey = YearMonth.now(ZoneOffset.UTC).toString();
        JsonNode reportsByMonth = getReportsByMonth(year);
        boolean foundCurrentMonth = false;
        for (JsonNode item : reportsByMonth) {
            if (monthKey.equals(item.get("month").asText())) {
                assertThat(item.get("reportCount").asLong()).isGreaterThanOrEqualTo(1);
                foundCurrentMonth = true;
                break;
            }
        }
        assertThat(foundCurrentMonth).isTrue();
    }

    private JsonNode getOverview() throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/statistics/overview?range=MONTH")
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return root.get("data");
    }

    private JsonNode getCollectorsByArea() throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/statistics/collectors-by-area")
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return root.get("data");
    }

    private JsonNode getReportsByMonth(int year) throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/statistics/reports-by-month?year=" + year)
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return root.get("data");
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
        public String description = "Admin statistics test";
        public BigDecimal estimatedWeightKg = new BigDecimal("1.250");
        public BigDecimal latitude = new BigDecimal("10.762622");
        public BigDecimal longitude = new BigDecimal("106.660172");
        public String addressText = "Stats admin";
        public List<String> imageUrls = List.of("https://example.com/stats-admin.jpg");
    }
}
