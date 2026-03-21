package com.crowdsourced.wasteplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crowdsourced.wasteplatform.entity.CollectorStatus;
import com.crowdsourced.wasteplatform.entity.Role;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.UserRole;
import com.crowdsourced.wasteplatform.entity.UserStatus;
import com.crowdsourced.wasteplatform.entity.UserType;
import com.crowdsourced.wasteplatform.repository.ReportAssignmentRepository;
import com.crowdsourced.wasteplatform.repository.ReportStatusHistoryRepository;
import com.crowdsourced.wasteplatform.repository.RoleRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.UserRoleRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(classes = com.crowdsourced.wasteplatform.WastePlatformApplication.class)
@AutoConfigureMockMvc
class CollectorCancelIntegrationTest {

    private static final UUID AREA_HCM_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID CATEGORY_PLASTIC_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReportAssignmentRepository assignmentRepository;

    @Autowired
    private ReportStatusHistoryRepository historyRepository;

    private String citizenToken;
    private String managerToken;
    private String collector1Token;
    private String collector2Token;
    private UUID collector1Id;

    @BeforeEach
    void setup() throws Exception {
        if (citizenToken == null) {
            citizenToken = loginAndGetAccessToken("citizen@example.com", "Password@123");
        }
        if (managerToken == null) {
            managerToken = loginAndGetAccessToken("enterprise@example.com", "Password@123");
        }

        collector1Id = userRepository.findByEmail("collector@example.com")
            .map(User::getId)
            .orElseThrow();
        User collector1 = userRepository.findById(collector1Id).orElseThrow();
        collector1.setAreaId(AREA_HCM_ID);
        userRepository.save(collector1);

        ensureSecondCollectorExists();

        collector1Token = loginAndGetAccessToken("collector@example.com", "Password@123");
        collector2Token = loginAndGetAccessToken("collector2@example.com", "Password@123");
    }

    @Test
    void collectorCancelSuccess_whenAssigned() throws Exception {
        String reportId = createAcceptedAssignedReport(collector1Id);
        String assignmentId = getAssignmentIdByReportForCollector(reportId, collector1Token);

        String cancelPayload = """
            {"reason":"Ket xe, khong den kip"}
            """;

        MvcResult cancelRes = mockMvc.perform(post("/collector/assignments/" + assignmentId + "/cancel")
                .header("Authorization", "Bearer " + collector1Token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(cancelPayload))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode node = objectMapper.readTree(cancelRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(node.get("data").get("collectorStatus").asText()).isEqualTo("FAILED");
        assertThat(node.get("data").get("currentStatus").asText()).isEqualTo("ACCEPTED");

        UUID reportUuid = UUID.fromString(reportId);
        assertThat(historyRepository.findByReportIdOrderByCreatedAtAsc(reportUuid)
            .stream()
            .anyMatch(h -> h.getToStatus().name().equals("ACCEPTED") && h.getNote() != null && h.getNote().startsWith("Collector cancelled:")))
            .isTrue();
    }

    @Test
    void collectorCancelSuccess_whenOnTheWay() throws Exception {
        String reportId = createAcceptedAssignedReport(collector1Id);
        String assignmentId = getAssignmentIdByReportForCollector(reportId, collector1Token);

        String toOnTheWayPayload = """
            {"status":"ON_THE_WAY","note":"Dang di thu gom"}
            """;
        mockMvc.perform(patch("/collector/assignments/" + assignmentId + "/status")
                .header("Authorization", "Bearer " + collector1Token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toOnTheWayPayload))
            .andExpect(status().isOk());

        String cancelPayload = """
            {"reason":"Xe hong giua duong"}
            """;
        MvcResult cancelRes = mockMvc.perform(post("/collector/assignments/" + assignmentId + "/cancel")
                .header("Authorization", "Bearer " + collector1Token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(cancelPayload))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode node = objectMapper.readTree(cancelRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(node.get("data").get("collectorStatus").asText()).isEqualTo("FAILED");
        assertThat(node.get("data").get("currentStatus").asText()).isEqualTo("ACCEPTED");
    }

    @Test
    void collectorCancelFail_whenCollected() throws Exception {
        String reportId = createAcceptedAssignedReport(collector1Id);
        String assignmentId = getAssignmentIdByReportForCollector(reportId, collector1Token);

        UUID assignmentUuid = UUID.fromString(assignmentId);
        var assignment = assignmentRepository.findById(assignmentUuid).orElseThrow();
        assignment.setCollectorStatus(CollectorStatus.COLLECTED);
        assignmentRepository.save(assignment);

        String cancelPayload = """
            {"reason":"Muon huy sau collected"}
            """;
        MvcResult cancelRes = mockMvc.perform(post("/collector/assignments/" + assignmentId + "/cancel")
                .header("Authorization", "Bearer " + collector1Token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(cancelPayload))
            .andExpect(status().isBadRequest())
            .andReturn();

        JsonNode node = objectMapper.readTree(cancelRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(node.get("code").asText()).isEqualTo("INVALID_ASSIGNMENT_STATUS_FOR_CANCEL");
    }

    @Test
    void collectorCancelFail_whenAssignmentBelongsToAnotherCollector() throws Exception {
        String reportId = createAcceptedAssignedReport(collector1Id);
        String assignmentId = getAssignmentIdByReportForCollector(reportId, collector1Token);

        String cancelPayload = """
            {"reason":"Khong phai assignment cua toi"}
            """;
        MvcResult cancelRes = mockMvc.perform(post("/collector/assignments/" + assignmentId + "/cancel")
                .header("Authorization", "Bearer " + collector2Token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(cancelPayload))
            .andExpect(status().isForbidden())
            .andReturn();

        JsonNode node = objectMapper.readTree(cancelRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(node.get("code").asText()).isEqualTo("ACCESS_DENIED");
    }

    private String createAcceptedAssignedReport(UUID collectorId) throws Exception {
        String createPayload = objectMapper.writeValueAsString(new CreatePayload());
        MvcResult createRes = mockMvc.perform(post("/citizen/reports")
                .header("Authorization", "Bearer " + citizenToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPayload))
            .andExpect(status().isOk())
            .andReturn();
        String reportId = objectMapper.readTree(createRes.getResponse().getContentAsString(StandardCharsets.UTF_8))
            .get("data").get("id").asText();

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

        return reportId;
    }

    private String getAssignmentIdByReportForCollector(String reportId, String collectorToken) throws Exception {
        MvcResult listRes = mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .get("/collector/assignments?status=ASSIGNED&page=0&size=20")
                .header("Authorization", "Bearer " + collectorToken))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode listNode = objectMapper.readTree(listRes.getResponse().getContentAsString(StandardCharsets.UTF_8));
        for (JsonNode node : listNode.get("data").get("content")) {
            if (reportId.equals(node.get("reportId").asText())) {
                return node.get("assignmentId").asText();
            }
        }
        throw new IllegalStateException("Assignment not found in collector list");
    }

    private void ensureSecondCollectorExists() {
        User collector2 = userRepository.findByEmail("collector2@example.com")
            .orElseGet(() -> userRepository.save(User.builder()
                .email("collector2@example.com")
                .passwordHash(passwordEncoder.encode("Password@123"))
                .fullName("Collector Two")
                .userType(UserType.COLLECTOR)
                .status(UserStatus.ACTIVE)
                .areaId(AREA_HCM_ID)
                .build()));

        collector2.setAreaId(AREA_HCM_ID);
        collector2.setUserType(UserType.COLLECTOR);
        collector2.setStatus(UserStatus.ACTIVE);
        collector2.setPasswordHash(passwordEncoder.encode("Password@123"));
        userRepository.save(collector2);

        Role collectorRole = roleRepository.findByCode("ROLE_COLLECTOR").orElseThrow();
        if (!userRoleRepository.existsByUserIdAndRoleId(collector2.getId(), collectorRole.getId())) {
            userRoleRepository.save(UserRole.builder()
                .userId(collector2.getId())
                .roleId(collectorRole.getId())
                .build());
        }
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
        public String description = "Collector cancel test";
        public BigDecimal estimatedWeightKg = new BigDecimal("2.5");
        public String addressText = "Collector cancel test address";
        public List<String> imageUrls = List.of("https://example.com/cancel-test.jpg");
    }
}
