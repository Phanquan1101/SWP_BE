-- Domain master schema

CREATE TABLE IF NOT EXISTS areas (
    id CHAR(36) NOT NULL,
    parent_id CHAR(36) NULL,
    name VARCHAR(255) NOT NULL,
    is_active TINYINT(1) NOT NULL DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_areas_parent FOREIGN KEY (parent_id) REFERENCES areas(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS waste_categories (
    id CHAR(36) NOT NULL,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    is_active TINYINT(1) NOT NULL DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_waste_categories_code (code)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS enterprises (
    id CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    tax_code VARCHAR(50) NULL,
    address_text VARCHAR(255) NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS enterprise_waste_capabilities (
    id CHAR(36) NOT NULL,
    enterprise_id CHAR(36) NOT NULL,
    waste_category_id CHAR(36) NOT NULL,
    daily_capacity_kg DECIMAL(12,3) NOT NULL DEFAULT 0,
    is_accepting TINYINT(1) NOT NULL DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_enterprise_waste (enterprise_id, waste_category_id),
    CONSTRAINT fk_cap_enterprise FOREIGN KEY (enterprise_id) REFERENCES enterprises(id),
    CONSTRAINT fk_cap_waste_category FOREIGN KEY (waste_category_id) REFERENCES waste_categories(id)
) ENGINE=InnoDB;
