package com.crowdsourced.wasteplatform;

import com.crowdsourced.wasteplatform.entity.Role;
import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.entity.UserStatus;
import com.crowdsourced.wasteplatform.entity.UserType;
import com.crowdsourced.wasteplatform.entity.UserRole;
import com.crowdsourced.wasteplatform.repository.RoleRepository;
import com.crowdsourced.wasteplatform.repository.UserRepository;
import com.crowdsourced.wasteplatform.repository.UserRoleRepository;
import com.crowdsourced.wasteplatform.service.auth.JwtService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SecurityIntegrationTest.TestApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityIntegrationTest {

    private static final String PASSWORD = "Password@123";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User adminUser;
    private User citizenUser;

    @BeforeEach
    void setUp() {
        userRoleRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        adminUser = seedUserWithRole(
            "admin@example.com",
            "Admin User",
            UserType.ADMIN,
            "ROLE_ADMIN"
        );

        citizenUser = seedUserWithRole(
            "citizen@example.com",
            "Citizen User",
            UserType.CITIZEN,
            "ROLE_CITIZEN"
        );
    }

    @Test
    void publicHealthAccessibleWithoutToken() throws Exception {
        mockMvc.perform(get("/health/public"))
            .andExpect(status().isOk());
    }

    @Test
    void protectedHealthRequiresToken() throws Exception {
        mockMvc.perform(get("/health/protected"))
            .andExpect(status().isUnauthorized());

        String token = jwtService.generateAccessToken(citizenUser, List.of("ROLE_CITIZEN"));
        mockMvc.perform(get("/health/protected")
            .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
    }

    @Test
    void adminPingRoleBased() throws Exception {
        String citizenToken = jwtService.generateAccessToken(citizenUser, List.of("ROLE_CITIZEN"));
        mockMvc.perform(get("/admin/ping")
            .header("Authorization", "Bearer " + citizenToken))
            .andExpect(status().isForbidden());

        String adminToken = jwtService.generateAccessToken(adminUser, List.of("ROLE_ADMIN"));
        mockMvc.perform(get("/admin/ping")
            .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isOk());
    }

    private User seedUserWithRole(String email, String fullName, UserType userType, String roleCode) {
        Role role = roleRepository.findByCode(roleCode)
            .orElseGet(() -> roleRepository.save(Role.builder()
                .code(roleCode)
                .name(roleCode)
                .description(roleCode)
                .build()));

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
            .roleId(role.getId())
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
