package com.crowdsourced.wasteplatform.config;

import com.crowdsourced.wasteplatform.exception.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class CitizenReportRateLimitFilter extends OncePerRequestFilter {

    private static final String TARGET_PATH = "/citizen/reports";
    private static final String RATE_LIMIT_KEY_PREFIX = "rate_limit:citizen_reports:";
    private static final String RATE_LIMIT_CODE = "RATE_LIMIT_EXCEEDED";
    private static final String RATE_LIMIT_MESSAGE = "Bạn đã gửi quá nhiều báo cáo trong thời gian ngắn. Vui lòng thử lại sau.";
    private static final int HTTP_TOO_MANY_REQUESTS = 429;

    private static final RedisScript<Long> INCREMENT_WITH_TTL_SCRIPT = new DefaultRedisScript<>(
        """
            local current = redis.call('INCR', KEYS[1])
            if current == 1 then
              redis.call('EXPIRE', KEYS[1], tonumber(ARGV[1]))
            end
            return current
            """,
        Long.class
    );

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private final int maxRequestsPerWindow;
    private final Duration windowDuration;

    public CitizenReportRateLimitFilter(StringRedisTemplate redisTemplate,
                                        ObjectMapper objectMapper,
                                        @Value("${app.rate-limit.citizen-reports.max-requests:5}") int maxRequestsPerWindow,
                                        @Value("${app.rate-limit.citizen-reports.window-seconds:60}") long windowSeconds) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.maxRequestsPerWindow = maxRequestsPerWindow;
        this.windowDuration = Duration.ofSeconds(windowSeconds);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !HttpMethod.POST.matches(request.getMethod()) || !TARGET_PATH.equals(request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String userId = resolveAuthenticatedUserId();
        if (userId == null) {
            // Theo convention security hien tai, endpoint nay bat buoc auth va principal la userId.
            // Neu vi ly do nao do khong lay duoc userId thi fail-open de khong chan nham request hop le.
            filterChain.doFilter(request, response);
            return;
        }

        String redisKey = RATE_LIMIT_KEY_PREFIX + userId;
        try {
            Long currentCount = redisTemplate.execute(
                INCREMENT_WITH_TTL_SCRIPT,
                Collections.singletonList(redisKey),
                String.valueOf(windowDuration.getSeconds())
            );

            if (currentCount != null && currentCount > maxRequestsPerWindow) {
                writeRateLimitExceeded(response);
                return;
            }
        } catch (Exception ex) {
            // Redis dung de chia se state giua instance. Neu Redis tam loi thi fail-open cho MVP
            // de khong lam ngat luong tao report cua user.
            log.warn("Rate limit Redis unavailable for user {}. Apply fail-open behavior.", userId, ex);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UUID uuid) {
            return uuid.toString();
        }
        if (principal instanceof String value && !value.isBlank() && !"anonymousUser".equals(value)) {
            return value;
        }
        return null;
    }

    private void writeRateLimitExceeded(HttpServletResponse response) throws IOException {
        response.setStatus(HTTP_TOO_MANY_REQUESTS);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), ApiResponse.error(RATE_LIMIT_CODE, RATE_LIMIT_MESSAGE));
    }
}
