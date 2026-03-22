package com.crowdsourced.wasteplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import com.crowdsourced.wasteplatform.config.CitizenReportRateLimitFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class CitizenReportRateLimitFilterTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    private AutoCloseable mocks;
    private ObjectMapper objectMapper;
    private CitizenReportRateLimitFilter filter;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper().findAndRegisterModules();
        filter = new CitizenReportRateLimitFilter(redisTemplate, objectMapper, 5, 60);

        UUID userId = UUID.randomUUID();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userId,
            null,
            java.util.List.of(new SimpleGrantedAuthority("ROLE_CITIZEN"))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void tearDown() throws Exception {
        SecurityContextHolder.clearContext();
        mocks.close();
    }

    @Test
    void sameUserSixRequestsWithinOneMinute_shouldBlockSixthRequest() throws Exception {
        AtomicLong counter = new AtomicLong(0);
        when(redisTemplate.execute(any(), anyList(), any()))
            .thenAnswer(invocation -> counter.incrementAndGet());

        for (int i = 0; i < 5; i++) {
            MockHttpServletResponse response = invokeCitizenReportPost();
            assertThat(response.getStatus()).isEqualTo(200);
        }

        MockHttpServletResponse blocked = invokeCitizenReportPost();
        assertThat(blocked.getStatus()).isEqualTo(429);

        JsonNode body = objectMapper.readTree(blocked.getContentAsString(StandardCharsets.UTF_8));
        assertThat(body.get("success").asBoolean()).isFalse();
        assertThat(body.get("code").asText()).isEqualTo("RATE_LIMIT_EXCEEDED");
    }

    @Test
    void redisUnavailable_shouldFailOpen() throws Exception {
        when(redisTemplate.execute(any(), anyList(), any()))
            .thenThrow(new RuntimeException("Redis connection error"));

        MockHttpServletResponse response = invokeCitizenReportPost();
        assertThat(response.getStatus()).isEqualTo(200);
    }

    private MockHttpServletResponse invokeCitizenReportPost() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/citizen/reports");
        request.setServletPath("/citizen/reports");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, (req, res) -> ((HttpServletResponse) res).setStatus(200));
        return response;
    }
}
