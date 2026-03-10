package com.crowdsourced.wasteplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crowdsourced.wasteplatform.entity.PointTransaction;
import com.crowdsourced.wasteplatform.entity.TxType;
import com.crowdsourced.wasteplatform.entity.Voucher;
import com.crowdsourced.wasteplatform.repository.PointTransactionRepository;
import com.crowdsourced.wasteplatform.repository.VoucherRedemptionRepository;
import com.crowdsourced.wasteplatform.repository.VoucherRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(classes = com.crowdsourced.wasteplatform.WastePlatformApplication.class)
@AutoConfigureMockMvc
class VoucherMarketplaceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherRedemptionRepository voucherRedemptionRepository;

    @Autowired
    private PointTransactionRepository pointTransactionRepository;

    private String enterpriseToken;

    @BeforeEach
    void setUp() throws Exception {
        if (enterpriseToken == null) {
            enterpriseToken = loginAndGetAccessToken("enterprise@example.com", "Password@123");
        }
    }

    @Test
    void enterpriseCreatesVoucherSuccessfully() throws Exception {
        String code = "VCR-CREATE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String body = """
            {
              "code": "%s",
              "title": "Voucher Tao Moi",
              "description": "Mo ta voucher",
              "pointsCost": 50,
              "stock": 10,
              "isActive": true,
              "imageUrl": "https://example.com/voucher.jpg"
            }
            """.formatted(code);

        MvcResult result = mockMvc.perform(post("/enterprise/vouchers")
                .header("Authorization", "Bearer " + enterpriseToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode data = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8)).path("data");
        assertThat(data.path("code").asText()).isEqualTo(code);
        assertThat(data.path("displayStatus").asText()).isEqualTo("OPEN");
    }

    @Test
    void publicListReturnsComputedDisplayStatus() throws Exception {
        String voucherId = createVoucher("VCR-PUBLIC-" + shortId(), 30, 20, null, null, true);

        MvcResult result = mockMvc.perform(get("/vouchers?page=0&size=20"))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode content = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8))
            .path("data").path("content");

        JsonNode row = findById(content, voucherId);
        assertThat(row).isNotNull();
        assertThat(row.path("displayStatus").asText()).isEqualTo("OPEN");
    }

    @Test
    void citizenRedeemSuccess_decreaseStock_createRedemption_andNegativePointTx() throws Exception {
        RegisteredCitizen citizen = registerCitizen("redeem-success");
        seedPoints(citizen.userId(), 200);
        String voucherId = createVoucher("VCR-SUCCESS-" + shortId(), 120, 3, null, null, true);

        MvcResult redeemResult = mockMvc.perform(post("/citizen/vouchers/" + voucherId + "/redeem")
                .header("Authorization", "Bearer " + citizen.accessToken()))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode redeemData = objectMapper.readTree(redeemResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
            .path("data");
        assertThat(redeemData.path("voucherId").asText()).isEqualTo(voucherId);
        assertThat(redeemData.path("remainingPoints").asLong()).isEqualTo(80);

        Voucher voucher = voucherRepository.findById(UUID.fromString(voucherId)).orElseThrow();
        assertThat(voucher.getStock()).isEqualTo(2);

        long redemptionCount = voucherRedemptionRepository
            .findByUserIdOrderByRedeemedAtDesc(UUID.fromString(citizen.userId()), PageRequest.of(0, 20))
            .stream()
            .filter(r -> r.getVoucherId().toString().equals(voucherId))
            .count();
        assertThat(redemptionCount).isEqualTo(1);

        boolean hasNegativeRedeemTx = pointTransactionRepository
            .findAllByUserIdOrderByCreatedAtDesc(UUID.fromString(citizen.userId()), PageRequest.of(0, 50))
            .stream()
            .anyMatch(tx -> tx.getTxType() == TxType.REDEEM && tx.getPoints() == -120);
        assertThat(hasNegativeRedeemTx).isTrue();
    }

    @Test
    void citizenRedeemFailWhenInsufficientPoints() throws Exception {
        RegisteredCitizen citizen = registerCitizen("redeem-insufficient");
        seedPoints(citizen.userId(), 10);
        String voucherId = createVoucher("VCR-INSUFF-" + shortId(), 100, 2, null, null, true);

        MvcResult result = mockMvc.perform(post("/citizen/vouchers/" + voucherId + "/redeem")
                .header("Authorization", "Bearer " + citizen.accessToken()))
            .andExpect(status().isBadRequest())
            .andReturn();

        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(root.path("code").asText()).isEqualTo("VOUCHER_INSUFFICIENT_POINTS");
    }

    @Test
    void citizenRedeemFailWhenComingSoon() throws Exception {
        RegisteredCitizen citizen = registerCitizen("redeem-coming");
        seedPoints(citizen.userId(), 500);
        Instant from = Instant.now().plus(1, ChronoUnit.DAYS);
        String voucherId = createVoucher("VCR-COMING-" + shortId(), 100, 2, from, null, true);

        MvcResult result = mockMvc.perform(post("/citizen/vouchers/" + voucherId + "/redeem")
                .header("Authorization", "Bearer " + citizen.accessToken()))
            .andExpect(status().isConflict())
            .andReturn();

        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(root.path("code").asText()).isEqualTo("VOUCHER_COMING_SOON");
    }

    @Test
    void citizenRedeemFailWhenOutOfStock() throws Exception {
        RegisteredCitizen citizen = registerCitizen("redeem-stock");
        seedPoints(citizen.userId(), 500);
        String voucherId = createVoucher("VCR-OOS-" + shortId(), 100, 0, null, null, true);

        MvcResult result = mockMvc.perform(post("/citizen/vouchers/" + voucherId + "/redeem")
                .header("Authorization", "Bearer " + citizen.accessToken()))
            .andExpect(status().isConflict())
            .andReturn();

        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        assertThat(root.path("code").asText()).isEqualTo("VOUCHER_OUT_OF_STOCK");
    }

    @Test
    void citizenRedemptionHistoryReturnsRedeemedVoucher() throws Exception {
        RegisteredCitizen citizen = registerCitizen("redeem-history");
        seedPoints(citizen.userId(), 500);
        String voucherId = createVoucher("VCR-HIS-" + shortId(), 100, 3, null, null, true);
        mockMvc.perform(post("/citizen/vouchers/" + voucherId + "/redeem")
                .header("Authorization", "Bearer " + citizen.accessToken()))
            .andExpect(status().isOk());

        MvcResult historyResult = mockMvc.perform(get("/citizen/vouchers/redemptions?page=0&size=20")
                .header("Authorization", "Bearer " + citizen.accessToken()))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode content = objectMapper.readTree(historyResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
            .path("data").path("content");
        assertThat(content.isArray()).isTrue();
        boolean found = false;
        for (JsonNode row : content) {
            if (voucherId.equals(row.path("voucherId").asText())) {
                found = true;
                break;
            }
        }
        assertThat(found).isTrue();
    }

    @Test
    void redeemConcurrency_stockOne_onlyOneSuccess() throws Exception {
        RegisteredCitizen citizen = registerCitizen("redeem-race");
        seedPoints(citizen.userId(), 500);
        String voucherId = createVoucher("VCR-RACE-" + shortId(), 100, 1, null, null, true);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            Callable<Integer> task = () -> mockMvc.perform(post("/citizen/vouchers/" + voucherId + "/redeem")
                    .header("Authorization", "Bearer " + citizen.accessToken()))
                .andReturn()
                .getResponse()
                .getStatus();

            List<Future<Integer>> futures = executor.invokeAll(List.of(task, task));
            List<Integer> statuses = new ArrayList<>();
            for (Future<Integer> future : futures) {
                statuses.add(future.get());
            }

            long successCount = statuses.stream().filter(s -> s == 200).count();
            assertThat(successCount).isEqualTo(1);

            Voucher voucher = voucherRepository.findById(UUID.fromString(voucherId)).orElseThrow();
            assertThat(voucher.getStock()).isEqualTo(0);
        } finally {
            executor.shutdownNow();
        }
    }

    private String createVoucher(String code, int pointsCost, int stock, Instant from, Instant to, boolean active) throws Exception {
        String body = """
            {
              "code": "%s",
              "title": "Voucher %s",
              "description": "Voucher test",
              "pointsCost": %d,
              "stock": %d,
              "availableFrom": %s,
              "availableTo": %s,
              "isActive": %s,
              "imageUrl": "https://example.com/voucher-%s.jpg"
            }
            """.formatted(
            code,
            code,
            pointsCost,
            stock,
            from == null ? "null" : "\"" + from.toString() + "\"",
            to == null ? "null" : "\"" + to.toString() + "\"",
            active,
            code.toLowerCase(Locale.ROOT)
        );
        MvcResult result = mockMvc.perform(post("/enterprise/vouchers")
                .header("Authorization", "Bearer " + enterpriseToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode data = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8)).path("data");
        return data.path("id").asText();
    }

    private RegisteredCitizen registerCitizen(String prefix) throws Exception {
        String email = prefix + "." + UUID.randomUUID() + "@example.com";
        String payload = """
            {
              "email": "%s",
              "password": "Password@123",
              "fullName": "Voucher %s"
            }
            """.formatted(email, prefix);
        MvcResult registerResult = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode registerData = objectMapper.readTree(registerResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
            .path("data");
        return new RegisteredCitizen(
            registerData.path("user").path("id").asText(),
            registerData.path("tokens").path("accessToken").asText()
        );
    }

    private void seedPoints(String userId, int points) {
        pointTransactionRepository.save(PointTransaction.builder()
            .userId(UUID.fromString(userId))
            .reportId(null)
            .txType(TxType.ADJUST)
            .points(points)
            .description("Seed points for voucher tests")
            .build());
    }

    private String loginAndGetAccessToken(String identifier, String password) throws Exception {
        String payload = """
            {"identifier":"%s","password":"%s"}
            """.formatted(identifier, password);
        MvcResult result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        return node.path("data").path("tokens").path("accessToken").asText();
    }

    private JsonNode findById(JsonNode content, String id) {
        for (JsonNode row : content) {
            if (id.equals(row.path("id").asText())) {
                return row;
            }
        }
        return null;
    }

    private String shortId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
    }

    private record RegisteredCitizen(String userId, String accessToken) {
    }
}
