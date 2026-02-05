-- V8: schema for waste reports, media, status history, assignments, reward, complaints, notifications

CREATE TABLE IF NOT EXISTS waste_reports (
    id CHAR(36) NOT NULL,
    citizen_id CHAR(36) NOT NULL,
    area_id CHAR(36) NOT NULL,
    waste_category_id CHAR(36) NOT NULL,
    description TEXT,
    estimated_weight_kg DECIMAL(12,3) NOT NULL,
    actual_weight_kg DECIMAL(12,3),
    latitude DECIMAL(10,7) NOT NULL,
    longitude DECIMAL(10,7) NOT NULL,
    address_text VARCHAR(255),
    current_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    allow_edit_until TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_waste_reports_citizen FOREIGN KEY (citizen_id) REFERENCES users(id),
    CONSTRAINT fk_waste_reports_area FOREIGN KEY (area_id) REFERENCES areas(id),
    CONSTRAINT fk_waste_reports_category FOREIGN KEY (waste_category_id) REFERENCES waste_categories(id),
    INDEX idx_wr_citizen_created (citizen_id, created_at),
    INDEX idx_wr_area_status (area_id, current_status),
    INDEX idx_wr_category_status (waste_category_id, current_status)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS report_media (
    id CHAR(36) NOT NULL,
    report_id CHAR(36) NOT NULL,
    media_type VARCHAR(30) NOT NULL,
    url TEXT NOT NULL,
    taken_at TIMESTAMP NULL,
    created_by CHAR(36) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_report_media_report FOREIGN KEY (report_id) REFERENCES waste_reports(id),
    CONSTRAINT fk_report_media_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    INDEX idx_rm_report_created (report_id, created_at),
    INDEX idx_rm_media_type (media_type)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS report_status_history (
    id CHAR(36) NOT NULL,
    report_id CHAR(36) NOT NULL,
    from_status VARCHAR(20) NULL,
    to_status VARCHAR(20) NOT NULL,
    changed_by CHAR(36) NULL,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_rsh_report FOREIGN KEY (report_id) REFERENCES waste_reports(id),
    CONSTRAINT fk_rsh_changed_by FOREIGN KEY (changed_by) REFERENCES users(id),
    INDEX idx_rsh_report_created (report_id, created_at),
    INDEX idx_rsh_to_status (to_status)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS report_assignments (
    id CHAR(36) NOT NULL,
    report_id CHAR(36) NOT NULL,
    collector_id CHAR(36) NOT NULL,
    assigned_by CHAR(36) NULL,
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    collector_status VARCHAR(20) NOT NULL DEFAULT 'ASSIGNED',
    last_known_latitude DECIMAL(10,7) NULL,
    last_known_longitude DECIMAL(10,7) NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_ra_report FOREIGN KEY (report_id) REFERENCES waste_reports(id),
    CONSTRAINT fk_ra_collector FOREIGN KEY (collector_id) REFERENCES users(id),
    CONSTRAINT fk_ra_assigned_by FOREIGN KEY (assigned_by) REFERENCES users(id),
    UNIQUE KEY uk_ra_report (report_id),
    INDEX idx_ra_collector_assigned (collector_id, assigned_at),
    INDEX idx_ra_status (collector_status)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS reward_rules (
    id CHAR(36) NOT NULL,
    waste_category_id CHAR(36) NOT NULL,
    points_per_kg DECIMAL(12,3) NOT NULL,
    bonus_quality_points INT NOT NULL DEFAULT 0,
    bonus_fast_complete_points INT NOT NULL DEFAULT 0,
    effective_from TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    effective_to TIMESTAMP NULL,
    priority INT NOT NULL DEFAULT 100,
    is_active TINYINT(1) NOT NULL DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_reward_rule_category FOREIGN KEY (waste_category_id) REFERENCES waste_categories(id),
    INDEX idx_rr_category_active (waste_category_id, is_active),
    INDEX idx_rr_effective_from (effective_from)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS point_transactions (
    id CHAR(36) NOT NULL,
    user_id CHAR(36) NOT NULL,
    report_id CHAR(36) NULL,
    tx_type VARCHAR(20) NOT NULL,
    points INT NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_pt_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_pt_report FOREIGN KEY (report_id) REFERENCES waste_reports(id),
    INDEX idx_pt_user_created (user_id, created_at),
    INDEX idx_pt_report (report_id),
    UNIQUE KEY uk_pt_report_type (report_id, tx_type)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS complaints (
    id CHAR(36) NOT NULL,
    complainant_id CHAR(36) NOT NULL,
    report_id CHAR(36) NULL,
    category VARCHAR(30) NOT NULL,
    description TEXT NOT NULL,
    latitude DECIMAL(10,7) NULL,
    longitude DECIMAL(10,7) NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN',
    resolved_by CHAR(36) NULL,
    resolution_note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_complainant FOREIGN KEY (complainant_id) REFERENCES users(id),
    CONSTRAINT fk_complaint_report FOREIGN KEY (report_id) REFERENCES waste_reports(id),
    CONSTRAINT fk_complaint_resolved_by FOREIGN KEY (resolved_by) REFERENCES users(id),
    INDEX idx_complaint_report_status (report_id, status),
    INDEX idx_complaint_status_created (status, created_at)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS notifications (
    id CHAR(36) NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    report_id CHAR(36) NULL,
    complaint_id CHAR(36) NULL,
    title VARCHAR(255) NOT NULL,
    body TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_notification_report FOREIGN KEY (report_id) REFERENCES waste_reports(id),
    CONSTRAINT fk_notification_complaint FOREIGN KEY (complaint_id) REFERENCES complaints(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_notifications (
    id CHAR(36) NOT NULL,
    notification_id CHAR(36) NOT NULL,
    user_id CHAR(36) NOT NULL,
    is_read TINYINT(1) NOT NULL DEFAULT 0,
    read_at TIMESTAMP NULL,
    delivery_channel VARCHAR(20),
    PRIMARY KEY (id),
    CONSTRAINT fk_un_notification FOREIGN KEY (notification_id) REFERENCES notifications(id),
    CONSTRAINT fk_un_user FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_un_user_read (user_id, is_read)
) ENGINE=InnoDB;
