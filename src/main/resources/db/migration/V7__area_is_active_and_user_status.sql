-- V7: add is_active to areas, normalize user status
-- Bổ sung cột is_active nếu chưa có (MySQL không hỗ trợ IF NOT EXISTS cho column)
SET @has_is_active := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'areas' AND column_name = 'is_active'
);
SET @sql_add_is_active := IF(@has_is_active = 0,
    'ALTER TABLE areas ADD COLUMN is_active TINYINT(1) NOT NULL DEFAULT 1 AFTER name',
    'SELECT 1');
PREPARE stmt FROM @sql_add_is_active;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

UPDATE areas SET is_active = 1 WHERE is_active IS NULL;

-- Chu?n h?a status ng??i d?ng: INACTIVE -> SUSPENDED (ph? h?p enum m?i)
UPDATE users SET status = 'SUSPENDED' WHERE status = 'INACTIVE';
