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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import com.crowdsourced.wasteplatform.entity.ReportMedia;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "waste_reports")
public class WasteReport {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "citizen_id", length = 36, nullable = false)
    private UUID citizenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", insertable = false, updatable = false)
    private User citizen;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "area_id", length = 36, nullable = false)
    private UUID areaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", insertable = false, updatable = false)
    private Area area;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "waste_category_id", length = 36, nullable = false)
    private UUID wasteCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_category_id", insertable = false, updatable = false)
    private WasteCategory wasteCategory;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "estimated_weight_kg", nullable = false, precision = 12, scale = 3)
    private BigDecimal estimatedWeightKg;

    @Column(name = "actual_weight_kg", precision = 12, scale = 3)
    private BigDecimal actualWeightKg;

    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "address_text", length = 255)
    private String addressText;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status", nullable = false, length = 20)
    private ReportStatus currentStatus;

    @Column(name = "allow_edit_until")
    private Instant allowEditUntil;

    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY, cascade = {}, orphanRemoval = false)
    private List<ReportMedia> mediaList = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        if (id == null) id = UUID.randomUUID();
        if (currentStatus == null) currentStatus = ReportStatus.PENDING;
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
