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
class CitizenWasteReportIntegrationTest {

    private static final UUID AREA_HCM_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private String citizenToken;
    private String managerToken;
    private UUID collectorId;

    @BeforeEach
    void setup() throws Exception {
        if (citizenToken == null) {
            citizenToken = loginAndGetAccessToken("citizen@example.com", "Password@123");
        }
        if (managerToken == null) {
            managerToken = loginAndGetAccessToken("enterprise@example.com", "Password@123");
        }
        collectorId = userRepository.findByEmail("collector@example.com")
            .map(User::getId)
            .orElseThrow();

        User collector = userRepository.findById(collectorId).orElseThrow();
        collector.setAreaId(AREA_HCM_ID);
        userRepository.save(collector);
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
            {"reason":"Khong can thu gom nua"}
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

    @Test
    void citizenCancelAssignedReport_failWithInvalidStatusForCancel() throws Exception {
        String reportId = createCitizenReport();

        mockMvc.perform(post("/enterprise/reports/" + reportId + "/accept")
                .header("Authorization", "Bearer " + managerToken))
            .andExpect(status().isOk());

        String assignPayload = """
            {"collectorId":"%s"}
            """.formatted(collectorId);

        mockMvc.perform(post("/enterprise/reports/" + reportId + "/assign")
                .header("Authorization", "Bearer " + managerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(assignPayload))
            .andExpect(status().isOk());

        String cancelPayload = """
            {"reason":"Muon huy sau khi da assign"}
            """;

        MvcResult cancelRes = mockMvc.perform(post("/citizen/reports/" + reportId + "/cancel")
                .header("Authorization", "Bearer " + citizenToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(cancelPayload))
            .andExpect(status().isBadRequest())
            .andReturn();

        JsonNode cancelNode = objectMapper.readTree(cancelRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(cancelNode.get("code").asText()).isEqualTo("INVALID_REPORT_STATUS_FOR_CANCEL");
    }

    private String createCitizenReport() throws Exception {
        String createPayload = objectMapper.writeValueAsString(new CreatePayload());
        MvcResult createRes = mockMvc.perform(post("/citizen/reports")
                .header("Authorization", "Bearer " + citizenToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPayload))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode createNode = objectMapper.readTree(createRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return createNode.get("data").get("id").asText();
    }

    private String loginAndGetAccessToken(String identifier, String password) throws Exception {
        String body = """
            {"identifier":"%s","password":"%s"}
            """.formatted(identifier, password);
        MvcResult result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return node.get("data").get("tokens").get("accessToken").asText();
    }

    static class CreatePayload {
        public String areaId = AREA_HCM_ID.toString();
        public String wasteCategoryId = "55555555-5555-5555-5555-555555555555";
        public String description = "Report test";
        public BigDecimal estimatedWeightKg = new BigDecimal("1.5");
        public String addressText = "Test address";
        public List<String> imageUrls = List.of("https://example.com/img1.jpg");
    }
}
