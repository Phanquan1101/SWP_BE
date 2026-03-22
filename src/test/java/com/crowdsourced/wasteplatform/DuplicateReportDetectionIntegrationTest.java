package com.crowdsourced.wasteplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(classes = com.crowdsourced.wasteplatform.WastePlatformApplication.class)
@AutoConfigureMockMvc
class DuplicateReportDetectionIntegrationTest {

    private static final String AREA_1 = "11111111-1111-1111-1111-111111111111";
    private static final String AREA_2 = "33333333-3333-3333-3333-333333333333";
    private static final String CATEGORY_1 = "55555555-5555-5555-5555-555555555555";
    private static final String CATEGORY_2 = "66666666-6666-6666-6666-666666666666";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void duplicateReport_sameCitizenAreaCategoryWithinWindow_returns409() throws Exception {
        String accessToken = registerAndGetToken();

        createReport(accessToken, AREA_1, CATEGORY_1, status().isOk());
        MvcResult duplicateRes = createReport(accessToken, AREA_1, CATEGORY_1, status().isConflict());

        JsonNode body = objectMapper.readTree(duplicateRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(body.get("code").asText()).isEqualTo("DUPLICATE_REPORT");
    }

    @Test
    void duplicateReport_differentCategoryOrArea_stillSuccess() throws Exception {
        String accessToken = registerAndGetToken();

        createReport(accessToken, AREA_1, CATEGORY_1, status().isOk());
        createReport(accessToken, AREA_1, CATEGORY_2, status().isOk());
        createReport(accessToken, AREA_2, CATEGORY_1, status().isOk());
    }

    @Test
    void duplicateReport_afterLookbackWindow_stillSuccess() throws Exception {
        String accessToken = registerAndGetToken();

        MvcResult first = createReport(accessToken, AREA_1, CATEGORY_1, status().isOk());
        String reportId = objectMapper.readTree(first.getResponse().getContentAsString(StandardCharsets.UTF_8))
            .get("data").get("id").asText();

        Instant oldCreatedAt = Instant.now().minus(11, ChronoUnit.MINUTES);
        jdbcTemplate.update(
            "UPDATE waste_reports SET created_at = ?, updated_at = ? WHERE id = ?",
            java.sql.Timestamp.from(oldCreatedAt),
            java.sql.Timestamp.from(oldCreatedAt),
            reportId
        );

        createReport(accessToken, AREA_1, CATEGORY_1, status().isOk());
    }

    private String registerAndGetToken() throws Exception {
        String unique = UUID.randomUUID().toString().substring(0, 8);
        String registerPayload = """
            {
              "email": "dup-%s@example.com",
              "password": "Password@123",
              "fullName": "Duplicate Test User"
            }
            """.formatted(unique);

        MvcResult registerRes = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerPayload))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode registerNode = objectMapper.readTree(registerRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return registerNode.get("data").get("tokens").get("accessToken").asText();
    }

    private MvcResult createReport(String accessToken, String areaId, String categoryId,
                                   org.springframework.test.web.servlet.ResultMatcher expectedStatus) throws Exception {
        String payload = objectMapper.writeValueAsString(new CreatePayload(areaId, categoryId));
        return mockMvc.perform(post("/citizen/reports")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(expectedStatus)
            .andReturn();
    }

    static class CreatePayload {
        public String areaId;
        public String wasteCategoryId;
        public String description = "duplicate detection test";
        public BigDecimal estimatedWeightKg = new BigDecimal("1.2");
        public String addressText = "Test duplicate address";
        public List<String> imageUrls = List.of("https://example.com/dup.jpg");

        CreatePayload(String areaId, String wasteCategoryId) {
            this.areaId = areaId;
            this.wasteCategoryId = wasteCategoryId;
        }
    }
}
