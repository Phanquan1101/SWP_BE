package com.crowdsourced.wasteplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(classes = com.crowdsourced.wasteplatform.WastePlatformApplication.class)
@AutoConfigureMockMvc
class AreaTreeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void areaTree_publicAndHasHcmRoot() throws Exception {
        MvcResult result = mockMvc.perform(get("/areas/tree"))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(json.get("data").get("id").asText()).isEqualTo("11111111-1111-1111-1111-111111111111");
        assertThat(json.get("data").get("name").asText()).isEqualTo("TP.HCM");
        assertThat(json.get("data").get("children").size()).isGreaterThanOrEqualTo(3);
    }
}
