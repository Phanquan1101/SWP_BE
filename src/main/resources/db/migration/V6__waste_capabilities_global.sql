-- V6: global waste capabilities (no enterprise)

CREATE TABLE IF NOT EXISTS waste_capabilities (
    id CHAR(36) NOT NULL,
    waste_category_id CHAR(36) NOT NULL,
    daily_capacity_kg DECIMAL(12,3) NOT NULL DEFAULT 0,
    is_accepting TINYINT(1) NOT NULL DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_waste_capabilities_category (waste_category_id),
    CONSTRAINT fk_waste_capabilities_category FOREIGN KEY (waste_category_id) REFERENCES waste_categories(id)
) ENGINE=InnoDB;
