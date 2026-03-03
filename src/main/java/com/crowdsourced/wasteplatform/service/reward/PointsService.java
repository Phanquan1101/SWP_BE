package com.crowdsourced.wasteplatform.service.reward;

import com.crowdsourced.wasteplatform.dto.common.PageResponse;
import com.crowdsourced.wasteplatform.dto.points.response.PointTransactionResponse;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.mapper.PointTransactionMapper;
import com.crowdsourced.wasteplatform.repository.PointTransactionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointsService {

    private final PointTransactionRepository pointTransactionRepository;
    private final PointTransactionMapper pointTransactionMapper;

    @Transactional(readOnly = true)
    public PageResponse<PointTransactionResponse> getCitizenTransactions(String citizenId, Pageable pageable) {
        UUID userId = parseUuid(citizenId, "citizenId");
        Page<PointTransactionResponse> page = pointTransactionRepository
            .findAllByUserIdOrderByCreatedAtDesc(userId, pageable)
            .map(pointTransactionMapper::toResponse);
        return PageResponse.from(page);
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.BAD_REQUEST, "Invalid UUID for " + field);
        }
    }
}
