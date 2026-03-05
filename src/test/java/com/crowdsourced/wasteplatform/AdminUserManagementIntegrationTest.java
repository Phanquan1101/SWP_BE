package com.crowdsourced.wasteplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
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
class AdminUserManagementIntegrationTest {

    private static final String ACTIVE_AREA_ID = "33333333-3333-3333-3333-333333333333";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminToken;
    private String enterpriseToken;

    @BeforeEach
    void setUp() throws Exception {
        if (adminToken == null) {
            adminToken = loginAndGetAccessToken("admin@example.com", "Password@123");
            enterpriseToken = loginAndGetAccessToken("enterprise@example.com", "Password@123");
        }
    }

    @Test
    void assignCollectorRoleWithoutWorkingArea_returns400() throws Exception {
        String userId = registerCitizen("assign-without-area");

        MvcResult result = mockMvc.perform(post("/admin/users/" + userId + "/roles")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"roleCode":"COLLECTOR"}
                    """))
            .andExpect(status().isBadRequest())
            .andReturn();

        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(root.path("code").asText()).isEqualTo("WORKING_AREA_REQUIRED");
    }

    @Test
    void promoteCollectorWithArea_setsRoleAndWorkingArea() throws Exception {
        String userId = registerCitizen("promote-collector");

        MvcResult result = mockMvc.perform(post("/admin/users/" + userId + "/promote-collector")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"areaId":"%s"}
                    """.formatted(ACTIVE_AREA_ID)))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode data = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8)).path("data");
        assertThat(data.path("workingAreaId").asText()).isEqualTo(ACTIVE_AREA_ID);
        assertThat(data.path("roles").toString()).contains("ROLE_COLLECTOR");
    }

    @Test
    void enterpriseCollectorLookup_returnsPromotedCollector() throws Exception {
        String userId = registerCitizen("enterprise-lookup");
        promoteCollector(userId);

        MvcResult result = mockMvc.perform(get("/enterprise/collectors?areaId=" + ACTIVE_AREA_ID + "&page=0&size=20")
                .header("Authorization", "Bearer " + enterpriseToken))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode content = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8))
            .path("data")
            .path("content");

        boolean found = false;
        for (JsonNode item : content) {
            if (userId.equals(item.path("id").asText())) {
                found = true;
                assertThat(item.path("areaId").asText()).isEqualTo(ACTIVE_AREA_ID);
                break;
            }
        }
        assertThat(found).isTrue();
    }

    @Test
    void removeCollectorRole_autoClearsWorkingArea() throws Exception {
        String userId = registerCitizen("remove-collector");
        promoteCollector(userId);

        mockMvc.perform(delete("/admin/users/" + userId + "/roles/COLLECTOR")
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isOk());

        MvcResult detailResult = mockMvc.perform(get("/admin/users/" + userId)
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode data = objectMapper.readTree(detailResult.getResponse().getContentAsString(StandardCharsets.UTF_8)).path("data");
        assertThat(data.path("workingAreaId").isNull()).isTrue();
        assertThat(data.path("roles").toString()).doesNotContain("ROLE_COLLECTOR");
    }

    private void promoteCollector(String userId) throws Exception {
        mockMvc.perform(post("/admin/users/" + userId + "/promote-collector")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"areaId":"%s"}
                    """.formatted(ACTIVE_AREA_ID)))
            .andExpect(status().isOk());
    }

    private String registerCitizen(String prefix) throws Exception {
        String email = prefix + "." + UUID.randomUUID() + "@example.com";
        MvcResult result = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "email": "%s",
                      "password": "Password@123",
                      "fullName": "Admin User Mgmt %s"
                    }
                    """.formatted(email, prefix)))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return root.path("data").path("user").path("id").asText();
    }

    private String loginAndGetAccessToken(String identifier, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"identifier":"%s","password":"%s"}
                    """.formatted(identifier, password)))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return root.path("data").path("tokens").path("accessToken").asText();
    }
}
