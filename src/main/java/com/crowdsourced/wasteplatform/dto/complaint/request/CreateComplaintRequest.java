package com.crowdsourced.wasteplatform.dto.complaint.request;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.crowdsourced.wasteplatform.entity.ComplaintCategory;
import com.crowdsourced.wasteplatform.entity.ComplaintStatus;
import com.crowdsourced.wasteplatform.entity.WasteReport;

import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateComplaintRequest {
    private UUID id;

    private UUID reportId;

    private WasteReport report;

    private ComplaintCategory category;

    private String description;

    private java.math.BigDecimal latitude;

    private java.math.BigDecimal longitude;

    private ComplaintStatus status;

}
