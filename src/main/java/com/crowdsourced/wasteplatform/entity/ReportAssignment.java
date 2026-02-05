package com.crowdsourced.wasteplatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "report_assignments")
public class ReportAssignment {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "report_id", length = 36, nullable = false)
    private UUID reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", insertable = false, updatable = false)
    private WasteReport report;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "collector_id", length = 36, nullable = false)
    private UUID collectorId;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "assigned_by", length = 36)
    private UUID assignedBy;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "collector_status", length = 20, nullable = false)
    private CollectorStatus collectorStatus;

    @Column(name = "last_known_latitude", precision = 10, scale = 7)
    private BigDecimal lastKnownLatitude;

    @Column(name = "last_known_longitude", precision = 10, scale = 7)
    private BigDecimal lastKnownLongitude;

    @PrePersist
    protected void onCreate() {
        if (id == null) id = UUID.randomUUID();
        if (assignedAt == null) assignedAt = Instant.now();
        if (collectorStatus == null) collectorStatus = CollectorStatus.ASSIGNED;
    }
}
