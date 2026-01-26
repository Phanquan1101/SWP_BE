package com.crowdsourced.wasteplatform;

import com.crowdsourced.wasteplatform.dto.auth.request.LoginRequest;
import com.crowdsourced.wasteplatform.dto.auth.request.RefreshTokenRequest;
import com.crowdsourced.wasteplatform.dto.auth.response.LoginResponse;
import com.crowdsourced.wasteplatform.dto.auth.response.TokenResponse;
import com.crowdsourced.wasteplatform.entity.Role;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.UserStatus;
import com.crowdsourced.wasteplatform.entity.UserType;
import com.crowdsourced.wasteplatform.entity.UserRole;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.repository.RefreshTokenRepository;
import com.crowdsourced.wasteplatform.repository.RoleRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.UserRoleRepository;
import com.crowdsourced.wasteplatform.service.auth.AuthenticateService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = AuthenticateServiceTest.TestApplication.class)
@ActiveProfiles("test")
class AuthenticateServiceTest {

    private static final String PASSWORD = "Password@123";

    @Autowired
    private AuthenticateService authenticateService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User adminUser;

    @BeforeEach
    void setUp() {
        refreshTokenRepository.deleteAll();
        userRoleRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        adminUser = seedUserWithRole(
            "admin@example.com",
            "Admin User",
            UserType.ADMIN,
            "ROLE_ADMIN"
        );
    }

    @Test
    void loginSuccessWithSeededAdmin() {
        LoginRequest request = new LoginRequest();
        request.setIdentifier("admin@example.com");
        request.setPassword(PASSWORD);

        LoginResponse response = authenticateService.login(request);

        assertNotNull(response);
        assertNotNull(response.getTokens());
        assertNotNull(response.getTokens().getAccessToken());
        assertNotNull(response.getTokens().getRefreshToken());
        assertEquals("Bearer", response.getTokens().getTokenType());
        assertEquals(adminUser.getId(), response.getUser().getId());
        assertTrue(response.getUser().getRoles().contains("ROLE_ADMIN"));
    }

    @Test
    void loginFailsWithWrongPassword() {
        LoginRequest request = new LoginRequest();
        request.setIdentifier("admin@example.com");
        request.setPassword("WrongPassword");

        AppException ex = assertThrows(AppException.class, () -> authenticateService.login(request));
        assertEquals(ErrorCode.AUTH, ex.getErrorCode());
    }

    @Test
    void refreshSuccess() {
        LoginRequest request = new LoginRequest();
        request.setIdentifier("admin@example.com");
        request.setPassword(PASSWORD);
        LoginResponse loginResponse = authenticateService.login(request);

        RefreshTokenRequest refreshRequest = new RefreshTokenRequest();
        refreshRequest.setRefreshToken(loginResponse.getTokens().getRefreshToken());

        TokenResponse refreshed = authenticateService.refresh(refreshRequest);

        assertNotNull(refreshed);
        assertNotNull(refreshed.getAccessToken());
        assertEquals("Bearer", refreshed.getTokenType());
    }

    @Test
    void logoutRevokeThenRefreshFails() {
        LoginRequest request = new LoginRequest();
        request.setIdentifier("admin@example.com");
        request.setPassword(PASSWORD);
        LoginResponse loginResponse = authenticateService.login(request);

        authenticateService.logout(loginResponse.getTokens().getRefreshToken());

        RefreshTokenRequest refreshRequest = new RefreshTokenRequest();
        refreshRequest.setRefreshToken(loginResponse.getTokens().getRefreshToken());

        AppException ex = assertThrows(AppException.class, () -> authenticateService.refresh(refreshRequest));
        assertEquals(ErrorCode.AUTH, ex.getErrorCode());
    }

    private User seedUserWithRole(String email, String fullName, UserType userType, String roleCode) {
        Role role = Role.builder()
            .code(roleCode)
            .name(roleCode)
            .description(roleCode)
            .build();
        Role savedRole = roleRepository.save(role);

        User user = User.builder()
            .email(email)
            .phone(null)
            .passwordHash(passwordEncoder.encode(PASSWORD))
            .fullName(fullName)
            .userType(userType)
            .status(UserStatus.ACTIVE)
            .suspendedReason(null)
            .enterpriseId(null)
            .areaId(null)
            .build();
        User savedUser = userRepository.save(user);

        UserRole userRole = UserRole.builder()
            .userId(savedUser.getId())
            .roleId(savedRole.getId())
            .build();
        userRoleRepository.save(userRole);

        return savedUser;
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @ComponentScan("com.crowdsourced.wasteplatform")
    static class TestApplication {
    }
}
