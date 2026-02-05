-- V5: areas and user.area_id

CREATE TABLE IF NOT EXISTS areas (
    id CHAR(36) NOT NULL,
    parent_id CHAR(36) NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_areas_parent_v5 FOREIGN KEY (parent_id) REFERENCES areas(id)
) ENGINE=InnoDB;

-- Thêm cột area_id vào users nếu chưa có (MySQL không hỗ trợ IF NOT EXISTS cho column)
SET @has_area_id := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'users' AND column_name = 'area_id'
);
SET @sql_users_area := IF(@has_area_id = 0,
    'ALTER TABLE users ADD COLUMN area_id CHAR(36) NULL, ADD INDEX idx_users_area_id (area_id), ADD CONSTRAINT fk_users_area_v5 FOREIGN KEY (area_id) REFERENCES areas(id)',
    'SELECT 1');
PREPARE stmt FROM @sql_users_area;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
