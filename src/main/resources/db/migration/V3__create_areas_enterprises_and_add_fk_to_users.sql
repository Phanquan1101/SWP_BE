CREATE TABLE IF NOT EXISTS areas (
    id CHAR(36) NOT NULL,
    name VARCHAR(255),
    boundary MULTIPOLYGON NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- ===============================

CREATE TABLE IF NOT EXISTS enterprises (
    id CHAR(36) NOT NULL,
    name VARCHAR(255),
    tax_code VARCHAR(100),
    address_text VARCHAR(255),
    status VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- ===============================
ALTER TABLE users
    ADD INDEX idx_users_enterprise_id (enterprise_id),
    ADD INDEX idx_users_area_id (area_id);

-- ===============================

ALTER TABLE users
    ADD CONSTRAINT fk_users_enterprise
        FOREIGN KEY (enterprise_id)
        REFERENCES enterprises (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE;

ALTER TABLE users
    ADD CONSTRAINT fk_users_area
        FOREIGN KEY (area_id)
        REFERENCES areas (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE;
