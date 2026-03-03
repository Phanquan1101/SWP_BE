package com.crowdsourced.wasteplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crowdsourced.wasteplatform.entity.ReportStatus;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.WasteCapability;
import com.crowdsourced.wasteplatform.repository.ReportAssignmentRepository;
import com.crowdsourced.wasteplatform.repository.ReportStatusHistoryRepository;
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
class EnterpriseDispatchIntegrationTest {

    private static final UUID AREA_HCM_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID AREA_WARD_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");
    private static final UUID CATEGORY_PLASTIC_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WasteCapabilityRepository capabilityRepository;

    @Autowired
    private ReportAssignmentRepository assignmentRepository;

    @Autowired
    private ReportStatusHistoryRepository historyRepository;

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

        WasteCapability capability = capabilityRepository.findByWasteCategoryId(CATEGORY_PLASTIC_ID)
            .orElseGet(() -> WasteCapability.builder()
                .wasteCategoryId(CATEGORY_PLASTIC_ID)
                .dailyCapacityKg(new BigDecimal("1000.000"))
                .accepting(true)
                .build());
        capability.setAccepting(true);
        capabilityRepository.save(capability);
    }

    @Test
    void acceptPendingReport_success() throws Exception {
        String reportId = createCitizenReport();

        MvcResult acceptRes = mockMvc.perform(post("/enterprise/reports/" + reportId + "/accept")
                .header("Authorization", "Bearer " + managerToken))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode acceptNode = objectMapper.readTree(acceptRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(acceptNode.get("data").get("currentStatus").asText()).isEqualTo("ACCEPTED");
    }

    @Test
    void assignCollectorMismatchArea_returns400() throws Exception {
        String reportId = createCitizenReport();
        acceptReport(reportId);

        User collector = userRepository.findById(collectorId).orElseThrow();
        collector.setAreaId(AREA_WARD_ID);
        userRepository.save(collector);

        String assignPayload = """
            {"collectorId":"%s"}
            """.formatted(collectorId);

        mockMvc.perform(post("/enterprise/reports/" + reportId + "/assign")
                .header("Authorization", "Bearer " + managerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(assignPayload))
            .andExpect(status().isBadRequest());
    }

    @Test
    void assignSuccess_reportAssignedAndHistoryAppended() throws Exception {
        String reportId = createCitizenReport();
        acceptReport(reportId);

        User collector = userRepository.findById(collectorId).orElseThrow();
        collector.setAreaId(AREA_HCM_ID);
        userRepository.save(collector);

        String assignPayload = """
            {"collectorId":"%s"}
            """.formatted(collectorId);

        MvcResult assignRes = mockMvc.perform(post("/enterprise/reports/" + reportId + "/assign")
                .header("Authorization", "Bearer " + managerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(assignPayload))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode assignNode = objectMapper.readTree(assignRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(assignNode.get("data").get("currentStatus").asText()).isEqualTo("ASSIGNED");

        UUID reportUuid = UUID.fromString(reportId);
        assertThat(assignmentRepository.existsByReportId(reportUuid)).isTrue();
        assertThat(historyRepository.findByReportIdOrderByCreatedAtAsc(reportUuid)
            .stream()
            .anyMatch(h -> h.getToStatus() == ReportStatus.ASSIGNED)).isTrue();
    }

    private String createCitizenReport() throws Exception {
        String payload = objectMapper.writeValueAsString(new CreatePayload());
        MvcResult createRes = mockMvc.perform(post("/citizen/reports")
                .header("Authorization", "Bearer " + citizenToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode node = objectMapper.readTree(createRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return node.get("data").get("id").asText();
    }

    private void acceptReport(String reportId) throws Exception {
        mockMvc.perform(post("/enterprise/reports/" + reportId + "/accept")
                .header("Authorization", "Bearer " + managerToken))
            .andExpect(status().isOk());
    }

    private String loginAndGetAccessToken(String identifier, String password) throws Exception {
        String loginPayload = """
            {"identifier":"%s","password":"%s"}
            """.formatted(identifier, password);
        MvcResult result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginPayload))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return node.get("data").get("tokens").get("accessToken").asText();
    }

    static class CreatePayload {
        public String areaId = AREA_HCM_ID.toString();
        public String wasteCategoryId = CATEGORY_PLASTIC_ID.toString();
        public String description = "Enterprise flow test";
        public BigDecimal estimatedWeightKg = new BigDecimal("2.0");
        public BigDecimal latitude = new BigDecimal("10.762622");
        public BigDecimal longitude = new BigDecimal("106.660172");
        public String addressText = "Dispatch test address";
        public List<String> imageUrls = List.of("https://example.com/dispatch-test.jpg");
    }
}
