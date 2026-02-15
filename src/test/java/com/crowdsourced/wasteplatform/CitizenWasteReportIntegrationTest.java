package com.crowdsourced.wasteplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
class CitizenWasteReportIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String citizenToken;

    @BeforeEach
    void setup() throws Exception {
        if (citizenToken == null) {
            String body = """
                {"identifier":"citizen@example.com","password":"Password@123"}
                """;
            MvcResult result = mockMvc.perform(post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andReturn();
            JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
            citizenToken = node.get("data").get("tokens").get("accessToken").asText();
        }
    }

    @Test
    void citizenCreateListCancelReport_success() throws Exception {
        String createPayload = objectMapper.writeValueAsString(new CreatePayload());

        // Create
        MvcResult createRes = mockMvc.perform(post("/citizen/reports")
                .header("Authorization", "Bearer " + citizenToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPayload))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode createNode = objectMapper.readTree(createRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        String reportId = createNode.get("data").get("id").asText();
        assertThat(createNode.get("data").get("currentStatus").asText()).isEqualTo("PENDING");
        assertThat(createNode.get("data").get("statusHistory").size()).isEqualTo(1);

        // List
        MvcResult listRes = mockMvc.perform(get("/citizen/reports")
                .header("Authorization", "Bearer " + citizenToken))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode listNode = objectMapper.readTree(listRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        boolean found = false;
        for (JsonNode n : listNode.get("data").get("content")) {
            if (n.get("id").asText().equals(reportId)) {
                found = true;
                break;
            }
        }
        assertThat(found).isTrue();

        // Cancel
        String cancelPayload = """
            {"reason":"Không cần thu gom nữa"}
            """;
        MvcResult cancelRes = mockMvc.perform(post("/citizen/reports/" + reportId + "/cancel")
                .header("Authorization", "Bearer " + citizenToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(cancelPayload))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode cancelNode = objectMapper.readTree(cancelRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(cancelNode.get("data").get("currentStatus").asText()).isEqualTo("CANCELLED");
        assertThat(cancelNode.get("data").get("statusHistory").size()).isGreaterThanOrEqualTo(2);
    }

    /**
     * Simple DTO for building payload to avoid verbose map.
     */
    static class CreatePayload {
        public String areaId = "11111111-1111-1111-1111-111111111111";
        public String wasteCategoryId = "55555555-5555-5555-5555-555555555555";
        public String description = "Report test";
        public BigDecimal estimatedWeightKg = new BigDecimal("1.5");
        public BigDecimal latitude = new BigDecimal("10.762622");
        public BigDecimal longitude = new BigDecimal("106.660172");
        public String addressText = "Test address";
        public List<String> imageUrls = List.of("https://example.com/img1.jpg");
    }
}
