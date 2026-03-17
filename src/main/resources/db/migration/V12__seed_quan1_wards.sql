-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000001-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận 1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận 1',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000001-0000-0000-0000-000000000000';

-- Ward level for Quận 1
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000001-0000-0000-0000-000000000001', 'D0000001-0000-0000-0000-000000000000', 'Phường Bến Nghé', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000001-0000-0000-0000-000000000000',
    name = 'Phường Bến Nghé',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000001-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000001-0000-0000-0000-000000000002', 'D0000001-0000-0000-0000-000000000000', 'Phường Bến Thành', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000001-0000-0000-0000-000000000000',
    name = 'Phường Bến Thành',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000001-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000001-0000-0000-0000-000000000003', 'D0000001-0000-0000-0000-000000000000', 'Phường Cô Giang', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000001-0000-0000-0000-000000000000',
    name = 'Phường Cô Giang',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000001-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000001-0000-0000-0000-000000000004', 'D0000001-0000-0000-0000-000000000000', 'Phường Cầu Kho', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000001-0000-0000-0000-000000000000',
    name = 'Phường Cầu Kho',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000001-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000001-0000-0000-0000-000000000005', 'D0000001-0000-0000-0000-000000000000', 'Phường Cầu Ông Lãnh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000001-0000-0000-0000-000000000000',
    name = 'Phường Cầu Ông Lãnh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000001-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000001-0000-0000-0000-000000000006', 'D0000001-0000-0000-0000-000000000000', 'Phường Đa Kao', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000001-0000-0000-0000-000000000000',
    name = 'Phường Đa Kao',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000001-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000001-0000-0000-0000-000000000007', 'D0000001-0000-0000-0000-000000000000', 'Phường Nguyễn Cư Trinh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000001-0000-0000-0000-000000000000',
    name = 'Phường Nguyễn Cư Trinh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000001-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000001-0000-0000-0000-000000000008', 'D0000001-0000-0000-0000-000000000000', 'Phường Nguyễn Thái Bình', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000001-0000-0000-0000-000000000000',
    name = 'Phường Nguyễn Thái Bình',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000001-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000001-0000-0000-0000-000000000009', 'D0000001-0000-0000-0000-000000000000', 'Phường Phạm Ngũ Lão', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000001-0000-0000-0000-000000000000',
    name = 'Phường Phạm Ngũ Lão',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000001-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000001-0000-0000-0000-000000000010', 'D0000001-0000-0000-0000-000000000000', 'Phường Tân Định', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000001-0000-0000-0000-000000000000',
    name = 'Phường Tân Định',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000001-0000-0000-0000-000000000010';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000003-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận 3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận 3',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000003-0000-0000-0000-000000000000';

-- Ward level for Quận 3
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000003-0000-0000-0000-000000000001', 'D0000003-0000-0000-0000-000000000000', 'Phường 1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000003-0000-0000-0000-000000000000',
    name = 'Phường 1',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000003-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000003-0000-0000-0000-000000000002', 'D0000003-0000-0000-0000-000000000000', 'Phường 2', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000003-0000-0000-0000-000000000000',
    name = 'Phường 2',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000003-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000003-0000-0000-0000-000000000003', 'D0000003-0000-0000-0000-000000000000', 'Phường 3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000003-0000-0000-0000-000000000000',
    name = 'Phường 3',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000003-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000003-0000-0000-0000-000000000004', 'D0000003-0000-0000-0000-000000000000', 'Phường 4', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000003-0000-0000-0000-000000000000',
    name = 'Phường 4',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000003-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000003-0000-0000-0000-000000000005', 'D0000003-0000-0000-0000-000000000000', 'Phường 5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000003-0000-0000-0000-000000000000',
    name = 'Phường 5',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000003-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000003-0000-0000-0000-000000000006', 'D0000003-0000-0000-0000-000000000000', 'Phường 9', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000003-0000-0000-0000-000000000000',
    name = 'Phường 9',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000003-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000003-0000-0000-0000-000000000007', 'D0000003-0000-0000-0000-000000000000', 'Phường 10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000003-0000-0000-0000-000000000000',
    name = 'Phường 10',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000003-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000003-0000-0000-0000-000000000008', 'D0000003-0000-0000-0000-000000000000', 'Phường 11', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000003-0000-0000-0000-000000000000',
    name = 'Phường 11',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000003-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000003-0000-0000-0000-000000000009', 'D0000003-0000-0000-0000-000000000000', 'Phường 12', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000003-0000-0000-0000-000000000000',
    name = 'Phường 12',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000003-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000003-0000-0000-0000-000000000010', 'D0000003-0000-0000-0000-000000000000', 'Phường 13', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000003-0000-0000-0000-000000000000',
    name = 'Phường 13',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000003-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000003-0000-0000-0000-000000000011', 'D0000003-0000-0000-0000-000000000000', 'Phường 14', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000003-0000-0000-0000-000000000000',
    name = 'Phường 14',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000003-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000003-0000-0000-0000-000000000012', 'D0000003-0000-0000-0000-000000000000', 'Phường Võ Thị Sáu', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000003-0000-0000-0000-000000000000',
    name = 'Phường Võ Thị Sáu',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000003-0000-0000-0000-000000000012';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận 4', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận 4',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000000';

-- Ward level for Quận 4
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000001', 'D0000004-0000-0000-0000-000000000000', 'Phường 1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000004-0000-0000-0000-000000000000',
    name = 'Phường 1',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000002', 'D0000004-0000-0000-0000-000000000000', 'Phường 2', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000004-0000-0000-0000-000000000000',
    name = 'Phường 2',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000003', 'D0000004-0000-0000-0000-000000000000', 'Phường 3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000004-0000-0000-0000-000000000000',
    name = 'Phường 3',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000004', 'D0000004-0000-0000-0000-000000000000', 'Phường 4', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000004-0000-0000-0000-000000000000',
    name = 'Phường 4',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000005', 'D0000004-0000-0000-0000-000000000000', 'Phường 6', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000004-0000-0000-0000-000000000000',
    name = 'Phường 6',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000006', 'D0000004-0000-0000-0000-000000000000', 'Phường 8', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000004-0000-0000-0000-000000000000',
    name = 'Phường 8',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000007', 'D0000004-0000-0000-0000-000000000000', 'Phường 9', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000004-0000-0000-0000-000000000000',
    name = 'Phường 9',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000008', 'D0000004-0000-0000-0000-000000000000', 'Phường 10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000004-0000-0000-0000-000000000000',
    name = 'Phường 10',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000009', 'D0000004-0000-0000-0000-000000000000', 'Phường 13', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000004-0000-0000-0000-000000000000',
    name = 'Phường 13',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000010', 'D0000004-0000-0000-0000-000000000000', 'Phường 14', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000004-0000-0000-0000-000000000000',
    name = 'Phường 14',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000011', 'D0000004-0000-0000-0000-000000000000', 'Phường 15', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000004-0000-0000-0000-000000000000',
    name = 'Phường 15',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000012', 'D0000004-0000-0000-0000-000000000000', 'Phường 16', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000004-0000-0000-0000-000000000000',
    name = 'Phường 16',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000012';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000004-0000-0000-0000-000000000013', 'D0000004-0000-0000-0000-000000000000', 'Phường 18', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000004-0000-0000-0000-000000000000',
    name = 'Phường 18',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000004-0000-0000-0000-000000000013';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận 5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận 5',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000000';

-- Ward level for Quận 5
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000001', 'D0000005-0000-0000-0000-000000000000', 'Phường 1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 1',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000002', 'D0000005-0000-0000-0000-000000000000', 'Phường 2', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 2',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000003', 'D0000005-0000-0000-0000-000000000000', 'Phường 3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 3',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000004', 'D0000005-0000-0000-0000-000000000000', 'Phường 4', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 4',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000005', 'D0000005-0000-0000-0000-000000000000', 'Phường 5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 5',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000006', 'D0000005-0000-0000-0000-000000000000', 'Phường 6', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 6',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000007', 'D0000005-0000-0000-0000-000000000000', 'Phường 7', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 7',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000008', 'D0000005-0000-0000-0000-000000000000', 'Phường 8', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 8',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000009', 'D0000005-0000-0000-0000-000000000000', 'Phường 9', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 9',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000010', 'D0000005-0000-0000-0000-000000000000', 'Phường 10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 10',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000011', 'D0000005-0000-0000-0000-000000000000', 'Phường 11', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 11',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000012', 'D0000005-0000-0000-0000-000000000000', 'Phường 12', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 12',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000012';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000013', 'D0000005-0000-0000-0000-000000000000', 'Phường 13', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 13',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000013';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000005-0000-0000-0000-000000000014', 'D0000005-0000-0000-0000-000000000000', 'Phường 14', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000005-0000-0000-0000-000000000000',
    name = 'Phường 14',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000005-0000-0000-0000-000000000014';


-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận 6', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận 6',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000000';

-- Ward level for Quận 6
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000001', 'D0000006-0000-0000-0000-000000000000', 'Phường 1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 1',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000002', 'D0000006-0000-0000-0000-000000000000', 'Phường 2', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 2',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000003', 'D0000006-0000-0000-0000-000000000000', 'Phường 3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 3',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000004', 'D0000006-0000-0000-0000-000000000000', 'Phường 4', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 4',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000005', 'D0000006-0000-0000-0000-000000000000', 'Phường 5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 5',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000006', 'D0000006-0000-0000-0000-000000000000', 'Phường 6', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 6',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000007', 'D0000006-0000-0000-0000-000000000000', 'Phường 7', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 7',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000008', 'D0000006-0000-0000-0000-000000000000', 'Phường 8', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 8',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000009', 'D0000006-0000-0000-0000-000000000000', 'Phường 9', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 9',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000010', 'D0000006-0000-0000-0000-000000000000', 'Phường 10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 10',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000011', 'D0000006-0000-0000-0000-000000000000', 'Phường 11', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 11',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000012', 'D0000006-0000-0000-0000-000000000000', 'Phường 12', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 12',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000012';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000013', 'D0000006-0000-0000-0000-000000000000', 'Phường 13', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 13',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000013';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000006-0000-0000-0000-000000000014', 'D0000006-0000-0000-0000-000000000000', 'Phường 14', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000006-0000-0000-0000-000000000000',
    name = 'Phường 14',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000006-0000-0000-0000-000000000014';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000007-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận 7', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận 7',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000007-0000-0000-0000-000000000000';

-- Ward level for Quận 7
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000007-0000-0000-0000-000000000001', 'D0000007-0000-0000-0000-000000000000', 'Phường Bình Thuận', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000007-0000-0000-0000-000000000000',
    name = 'Phường Bình Thuận',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000007-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000007-0000-0000-0000-000000000002', 'D0000007-0000-0000-0000-000000000000', 'Phường Phú Mỹ', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000007-0000-0000-0000-000000000000',
    name = 'Phường Phú Mỹ',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000007-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000007-0000-0000-0000-000000000003', 'D0000007-0000-0000-0000-000000000000', 'Phường Phú Thuận', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000007-0000-0000-0000-000000000000',
    name = 'Phường Phú Thuận',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000007-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000007-0000-0000-0000-000000000004', 'D0000007-0000-0000-0000-000000000000', 'Phường Tân Hưng', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000007-0000-0000-0000-000000000000',
    name = 'Phường Tân Hưng',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000007-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000007-0000-0000-0000-000000000005', 'D0000007-0000-0000-0000-000000000000', 'Phường Tân Kiểng', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000007-0000-0000-0000-000000000000',
    name = 'Phường Tân Kiểng',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000007-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000007-0000-0000-0000-000000000006', 'D0000007-0000-0000-0000-000000000000', 'Phường Tân Phong', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000007-0000-0000-0000-000000000000',
    name = 'Phường Tân Phong',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000007-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000007-0000-0000-0000-000000000007', 'D0000007-0000-0000-0000-000000000000', 'Phường Tân Phú', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000007-0000-0000-0000-000000000000',
    name = 'Phường Tân Phú',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000007-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000007-0000-0000-0000-000000000008', 'D0000007-0000-0000-0000-000000000000', 'Phường Tân Quy', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000007-0000-0000-0000-000000000000',
    name = 'Phường Tân Quy',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000007-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000007-0000-0000-0000-000000000009', 'D0000007-0000-0000-0000-000000000000', 'Phường Tân Thuận Đông', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000007-0000-0000-0000-000000000000',
    name = 'Phường Tân Thuận Đông',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000007-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000007-0000-0000-0000-000000000010', 'D0000007-0000-0000-0000-000000000000', 'Phường Tân Thuận Tây', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000007-0000-0000-0000-000000000000',
    name = 'Phường Tân Thuận Tây',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000007-0000-0000-0000-000000000010';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận 8', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận 8',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000000';

-- Ward level for Quận 8
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000001', 'D0000008-0000-0000-0000-000000000000', 'Phường 1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 1',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000002', 'D0000008-0000-0000-0000-000000000000', 'Phường 2', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 2',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000003', 'D0000008-0000-0000-0000-000000000000', 'Phường 3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 3',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000004', 'D0000008-0000-0000-0000-000000000000', 'Phường 4', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 4',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000005', 'D0000008-0000-0000-0000-000000000000', 'Phường 5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 5',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000006', 'D0000008-0000-0000-0000-000000000000', 'Phường 6', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 6',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000007', 'D0000008-0000-0000-0000-000000000000', 'Phường 7', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 7',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000008', 'D0000008-0000-0000-0000-000000000000', 'Phường 8', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 8',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000009', 'D0000008-0000-0000-0000-000000000000', 'Phường 9', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 9',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000010', 'D0000008-0000-0000-0000-000000000000', 'Phường 10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 10',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000011', 'D0000008-0000-0000-0000-000000000000', 'Phường 11', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 11',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000012', 'D0000008-0000-0000-0000-000000000000', 'Phường 12', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 12',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000012';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000013', 'D0000008-0000-0000-0000-000000000000', 'Phường 13', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 13',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000013';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000014', 'D0000008-0000-0000-0000-000000000000', 'Phường 14', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 14',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000014';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000015', 'D0000008-0000-0000-0000-000000000000', 'Phường 15', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 15',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000015';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000008-0000-0000-0000-000000000016', 'D0000008-0000-0000-0000-000000000000', 'Phường 16', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000008-0000-0000-0000-000000000000',
    name = 'Phường 16',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000008-0000-0000-0000-000000000016';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận 10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận 10',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000000';

-- Ward level for Quận 10
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000001', 'D0000010-0000-0000-0000-000000000000', 'Phường 1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 1',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000002', 'D0000010-0000-0000-0000-000000000000', 'Phường 2', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 2',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000003', 'D0000010-0000-0000-0000-000000000000', 'Phường 4', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 4',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000004', 'D0000010-0000-0000-0000-000000000000', 'Phường 5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 5',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000005', 'D0000010-0000-0000-0000-000000000000', 'Phường 6', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 6',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000006', 'D0000010-0000-0000-0000-000000000000', 'Phường 7', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 7',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000007', 'D0000010-0000-0000-0000-000000000000', 'Phường 8', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 8',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000008', 'D0000010-0000-0000-0000-000000000000', 'Phường 9', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 9',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000009', 'D0000010-0000-0000-0000-000000000000', 'Phường 10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 10',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000010', 'D0000010-0000-0000-0000-000000000000', 'Phường 11', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 11',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000011', 'D0000010-0000-0000-0000-000000000000', 'Phường 12', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 12',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000012', 'D0000010-0000-0000-0000-000000000000', 'Phường 13', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 13',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000012';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000013', 'D0000010-0000-0000-0000-000000000000', 'Phường 14', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 14',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000013';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000010-0000-0000-0000-000000000014', 'D0000010-0000-0000-0000-000000000000', 'Phường 15', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000010-0000-0000-0000-000000000000',
    name = 'Phường 15',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000010-0000-0000-0000-000000000014';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận 11', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận 11',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000000';

-- Ward level for Quận 11
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000001', 'D0000011-0000-0000-0000-000000000000', 'Phường 1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 1',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000002', 'D0000011-0000-0000-0000-000000000000', 'Phường 2', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 2',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000003', 'D0000011-0000-0000-0000-000000000000', 'Phường 3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 3',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000004', 'D0000011-0000-0000-0000-000000000000', 'Phường 4', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 4',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000005', 'D0000011-0000-0000-0000-000000000000', 'Phường 5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 5',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000006', 'D0000011-0000-0000-0000-000000000000', 'Phường 6', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 6',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000007', 'D0000011-0000-0000-0000-000000000000', 'Phường 7', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 7',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000008', 'D0000011-0000-0000-0000-000000000000', 'Phường 8', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 8',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000009', 'D0000011-0000-0000-0000-000000000000', 'Phường 9', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 9',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000010', 'D0000011-0000-0000-0000-000000000000', 'Phường 10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 10',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000011', 'D0000011-0000-0000-0000-000000000000', 'Phường 11', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 11',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000012', 'D0000011-0000-0000-0000-000000000000', 'Phường 12', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 12',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000012';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000013', 'D0000011-0000-0000-0000-000000000000', 'Phường 13', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 13',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000013';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000014', 'D0000011-0000-0000-0000-000000000000', 'Phường 14', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 14',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000014';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000015', 'D0000011-0000-0000-0000-000000000000', 'Phường 15', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 15',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000015';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000011-0000-0000-0000-000000000016', 'D0000011-0000-0000-0000-000000000000', 'Phường 16', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000011-0000-0000-0000-000000000000',
    name = 'Phường 16',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000011-0000-0000-0000-000000000016';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000012-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận 12', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận 12',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000012-0000-0000-0000-000000000000';

-- Ward level for Quận 12
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000012-0000-0000-0000-000000000001', 'D0000012-0000-0000-0000-000000000000', 'Phường An Phú Đông', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000012-0000-0000-0000-000000000000',
    name = 'Phường An Phú Đông',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000012-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000012-0000-0000-0000-000000000002', 'D0000012-0000-0000-0000-000000000000', 'Phường Đông Hưng Thuận', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000012-0000-0000-0000-000000000000',
    name = 'Phường Đông Hưng Thuận',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000012-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000012-0000-0000-0000-000000000003', 'D0000012-0000-0000-0000-000000000000', 'Phường Hiệp Thành', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000012-0000-0000-0000-000000000000',
    name = 'Phường Hiệp Thành',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000012-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000012-0000-0000-0000-000000000004', 'D0000012-0000-0000-0000-000000000000', 'Phường Tân Chánh Hiệp', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000012-0000-0000-0000-000000000000',
    name = 'Phường Tân Chánh Hiệp',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000012-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000012-0000-0000-0000-000000000005', 'D0000012-0000-0000-0000-000000000000', 'Phường Tân Hưng Thuận', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000012-0000-0000-0000-000000000000',
    name = 'Phường Tân Hưng Thuận',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000012-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000012-0000-0000-0000-000000000006', 'D0000012-0000-0000-0000-000000000000', 'Phường Tân Thới Hiệp', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000012-0000-0000-0000-000000000000',
    name = 'Phường Tân Thới Hiệp',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000012-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000012-0000-0000-0000-000000000007', 'D0000012-0000-0000-0000-000000000000', 'Phường Tân Thới Nhất', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000012-0000-0000-0000-000000000000',
    name = 'Phường Tân Thới Nhất',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000012-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000012-0000-0000-0000-000000000008', 'D0000012-0000-0000-0000-000000000000', 'Phường Thạnh Lộc', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000012-0000-0000-0000-000000000000',
    name = 'Phường Thạnh Lộc',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000012-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000012-0000-0000-0000-000000000009', 'D0000012-0000-0000-0000-000000000000', 'Phường Thạnh Xuân', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000012-0000-0000-0000-000000000000',
    name = 'Phường Thạnh Xuân',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000012-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000012-0000-0000-0000-000000000010', 'D0000012-0000-0000-0000-000000000000', 'Phường Thới An', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000012-0000-0000-0000-000000000000',
    name = 'Phường Thới An',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000012-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000012-0000-0000-0000-000000000011', 'D0000012-0000-0000-0000-000000000000', 'Phường Trung Mỹ Tây', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000012-0000-0000-0000-000000000000',
    name = 'Phường Trung Mỹ Tây',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000012-0000-0000-0000-000000000011';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận Bình Thạnh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận Bình Thạnh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000000';

-- Ward level for Quận Bình Thạnh
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000001', 'D0000013-0000-0000-0000-000000000000', 'Phường 1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 1',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000002', 'D0000013-0000-0000-0000-000000000000', 'Phường 2', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 2',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000003', 'D0000013-0000-0000-0000-000000000000', 'Phường 3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 3',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000004', 'D0000013-0000-0000-0000-000000000000', 'Phường 5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 5',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000005', 'D0000013-0000-0000-0000-000000000000', 'Phường 6', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 6',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000006', 'D0000013-0000-0000-0000-000000000000', 'Phường 7', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 7',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000007', 'D0000013-0000-0000-0000-000000000000', 'Phường 11', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 11',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000008', 'D0000013-0000-0000-0000-000000000000', 'Phường 12', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 12',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000009', 'D0000013-0000-0000-0000-000000000000', 'Phường 13', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 13',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000010', 'D0000013-0000-0000-0000-000000000000', 'Phường 14', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 14',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000011', 'D0000013-0000-0000-0000-000000000000', 'Phường 15', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 15',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000012', 'D0000013-0000-0000-0000-000000000000', 'Phường 17', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 17',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000012';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000013', 'D0000013-0000-0000-0000-000000000000', 'Phường 19', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 19',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000013';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000014', 'D0000013-0000-0000-0000-000000000000', 'Phường 21', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 21',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000014';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000015', 'D0000013-0000-0000-0000-000000000000', 'Phường 22', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 22',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000015';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000016', 'D0000013-0000-0000-0000-000000000000', 'Phường 24', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 24',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000016';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000017', 'D0000013-0000-0000-0000-000000000000', 'Phường 25', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 25',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000017';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000018', 'D0000013-0000-0000-0000-000000000000', 'Phường 26', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 26',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000018';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000019', 'D0000013-0000-0000-0000-000000000000', 'Phường 27', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 27',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000019';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000013-0000-0000-0000-000000000020', 'D0000013-0000-0000-0000-000000000000', 'Phường 28', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000013-0000-0000-0000-000000000000',
    name = 'Phường 28',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000013-0000-0000-0000-000000000020';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận Gò Vấp', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận Gò Vấp',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000000';

-- Ward level for Quận Gò Vấp
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000001', 'D0000014-0000-0000-0000-000000000000', 'Phường 1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 1',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000002', 'D0000014-0000-0000-0000-000000000000', 'Phường 3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 3',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000003', 'D0000014-0000-0000-0000-000000000000', 'Phường 4', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 4',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000004', 'D0000014-0000-0000-0000-000000000000', 'Phường 5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 5',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000005', 'D0000014-0000-0000-0000-000000000000', 'Phường 6', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 6',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000006', 'D0000014-0000-0000-0000-000000000000', 'Phường 7', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 7',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000007', 'D0000014-0000-0000-0000-000000000000', 'Phường 8', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 8',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000008', 'D0000014-0000-0000-0000-000000000000', 'Phường 9', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 9',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000009', 'D0000014-0000-0000-0000-000000000000', 'Phường 10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 10',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000010', 'D0000014-0000-0000-0000-000000000000', 'Phường 11', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 11',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000011', 'D0000014-0000-0000-0000-000000000000', 'Phường 12', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 12',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000012', 'D0000014-0000-0000-0000-000000000000', 'Phường 13', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 13',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000012';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000013', 'D0000014-0000-0000-0000-000000000000', 'Phường 14', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 14',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000013';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000014', 'D0000014-0000-0000-0000-000000000000', 'Phường 15', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 15',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000014';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000015', 'D0000014-0000-0000-0000-000000000000', 'Phường 16', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 16',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000015';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000014-0000-0000-0000-000000000016', 'D0000014-0000-0000-0000-000000000000', 'Phường 17', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000014-0000-0000-0000-000000000000',
    name = 'Phường 17',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000014-0000-0000-0000-000000000016';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận Phú Nhuận', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận Phú Nhuận',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000000';

-- Ward level for Quận Phú Nhuận
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000001', 'D0000015-0000-0000-0000-000000000000', 'Phường 1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000015-0000-0000-0000-000000000000',
    name = 'Phường 1',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000002', 'D0000015-0000-0000-0000-000000000000', 'Phường 2', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000015-0000-0000-0000-000000000000',
    name = 'Phường 2',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000003', 'D0000015-0000-0000-0000-000000000000', 'Phường 3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000015-0000-0000-0000-000000000000',
    name = 'Phường 3',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000004', 'D0000015-0000-0000-0000-000000000000', 'Phường 4', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000015-0000-0000-0000-000000000000',
    name = 'Phường 4',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000005', 'D0000015-0000-0000-0000-000000000000', 'Phường 5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000015-0000-0000-0000-000000000000',
    name = 'Phường 5',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000006', 'D0000015-0000-0000-0000-000000000000', 'Phường 7', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000015-0000-0000-0000-000000000000',
    name = 'Phường 7',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000007', 'D0000015-0000-0000-0000-000000000000', 'Phường 8', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000015-0000-0000-0000-000000000000',
    name = 'Phường 8',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000008', 'D0000015-0000-0000-0000-000000000000', 'Phường 9', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000015-0000-0000-0000-000000000000',
    name = 'Phường 9',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000009', 'D0000015-0000-0000-0000-000000000000', 'Phường 10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000015-0000-0000-0000-000000000000',
    name = 'Phường 10',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000010', 'D0000015-0000-0000-0000-000000000000', 'Phường 11', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000015-0000-0000-0000-000000000000',
    name = 'Phường 11',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000011', 'D0000015-0000-0000-0000-000000000000', 'Phường 13', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000015-0000-0000-0000-000000000000',
    name = 'Phường 13',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000012', 'D0000015-0000-0000-0000-000000000000', 'Phường 15', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000015-0000-0000-0000-000000000000',
    name = 'Phường 15',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000012';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000015-0000-0000-0000-000000000013', 'D0000015-0000-0000-0000-000000000000', 'Phường 17', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000015-0000-0000-0000-000000000000',
    name = 'Phường 17',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000015-0000-0000-0000-000000000013';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận Tân Bình', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận Tân Bình',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000000';

-- Ward level for Quận Tân Bình
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000001', 'D0000016-0000-0000-0000-000000000000', 'Phường 1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 1',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000002', 'D0000016-0000-0000-0000-000000000000', 'Phường 2', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 2',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000003', 'D0000016-0000-0000-0000-000000000000', 'Phường 3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 3',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000004', 'D0000016-0000-0000-0000-000000000000', 'Phường 4', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 4',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000005', 'D0000016-0000-0000-0000-000000000000', 'Phường 5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 5',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000006', 'D0000016-0000-0000-0000-000000000000', 'Phường 6', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 6',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000007', 'D0000016-0000-0000-0000-000000000000', 'Phường 7', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 7',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000008', 'D0000016-0000-0000-0000-000000000000', 'Phường 8', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 8',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000009', 'D0000016-0000-0000-0000-000000000000', 'Phường 9', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 9',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000010', 'D0000016-0000-0000-0000-000000000000', 'Phường 10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 10',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000011', 'D0000016-0000-0000-0000-000000000000', 'Phường 11', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 11',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000012', 'D0000016-0000-0000-0000-000000000000', 'Phường 12', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 12',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000012';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000013', 'D0000016-0000-0000-0000-000000000000', 'Phường 13', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 13',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000013';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000014', 'D0000016-0000-0000-0000-000000000000', 'Phường 14', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 14',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000014';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000016-0000-0000-0000-000000000015', 'D0000016-0000-0000-0000-000000000000', 'Phường 15', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000016-0000-0000-0000-000000000000',
    name = 'Phường 15',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000016-0000-0000-0000-000000000015';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000017-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận Tân Phú', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận Tân Phú',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000017-0000-0000-0000-000000000000';

-- Ward level for Quận Tân Phú
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000017-0000-0000-0000-000000000001', 'D0000017-0000-0000-0000-000000000000', 'Phường Hiệp Tân', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000017-0000-0000-0000-000000000000',
    name = 'Phường Hiệp Tân',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000017-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000017-0000-0000-0000-000000000002', 'D0000017-0000-0000-0000-000000000000', 'Phường Hòa Thạnh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000017-0000-0000-0000-000000000000',
    name = 'Phường Hòa Thạnh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000017-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000017-0000-0000-0000-000000000003', 'D0000017-0000-0000-0000-000000000000', 'Phường Phú Thạnh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000017-0000-0000-0000-000000000000',
    name = 'Phường Phú Thạnh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000017-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000017-0000-0000-0000-000000000004', 'D0000017-0000-0000-0000-000000000000', 'Phường Phú Thọ Hòa', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000017-0000-0000-0000-000000000000',
    name = 'Phường Phú Thọ Hòa',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000017-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000017-0000-0000-0000-000000000005', 'D0000017-0000-0000-0000-000000000000', 'Phường Phú Trung', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000017-0000-0000-0000-000000000000',
    name = 'Phường Phú Trung',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000017-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000017-0000-0000-0000-000000000006', 'D0000017-0000-0000-0000-000000000000', 'Phường Sơn Kỳ', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000017-0000-0000-0000-000000000000',
    name = 'Phường Sơn Kỳ',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000017-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000017-0000-0000-0000-000000000007', 'D0000017-0000-0000-0000-000000000000', 'Phường Tân Quý', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000017-0000-0000-0000-000000000000',
    name = 'Phường Tân Quý',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000017-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000017-0000-0000-0000-000000000008', 'D0000017-0000-0000-0000-000000000000', 'Phường Tân Sơn Nhì', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000017-0000-0000-0000-000000000000',
    name = 'Phường Tân Sơn Nhì',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000017-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000017-0000-0000-0000-000000000009', 'D0000017-0000-0000-0000-000000000000', 'Phường Tân Thành', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000017-0000-0000-0000-000000000000',
    name = 'Phường Tân Thành',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000017-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000017-0000-0000-0000-000000000010', 'D0000017-0000-0000-0000-000000000000', 'Phường Tân Thới Hòa', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000017-0000-0000-0000-000000000000',
    name = 'Phường Tân Thới Hòa',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000017-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000017-0000-0000-0000-000000000011', 'D0000017-0000-0000-0000-000000000000', 'Phường Tây Thạnh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000017-0000-0000-0000-000000000000',
    name = 'Phường Tây Thạnh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000017-0000-0000-0000-000000000011';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000018-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Quận Bình Tân', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quận Bình Tân',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000018-0000-0000-0000-000000000000';

-- Ward level for Quận Bình Tân
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000018-0000-0000-0000-000000000001', 'D0000018-0000-0000-0000-000000000000', 'Phường An Lạc', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000018-0000-0000-0000-000000000000',
    name = 'Phường An Lạc',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000018-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000018-0000-0000-0000-000000000002', 'D0000018-0000-0000-0000-000000000000', 'Phường An Lạc A', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000018-0000-0000-0000-000000000000',
    name = 'Phường An Lạc A',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000018-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000018-0000-0000-0000-000000000003', 'D0000018-0000-0000-0000-000000000000', 'Phường Bình Hưng Hòa', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000018-0000-0000-0000-000000000000',
    name = 'Phường Bình Hưng Hòa',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000018-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000018-0000-0000-0000-000000000004', 'D0000018-0000-0000-0000-000000000000', 'Phường Bình Hưng Hòa A', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000018-0000-0000-0000-000000000000',
    name = 'Phường Bình Hưng Hòa A',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000018-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000018-0000-0000-0000-000000000005', 'D0000018-0000-0000-0000-000000000000', 'Phường Bình Hưng Hòa B', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000018-0000-0000-0000-000000000000',
    name = 'Phường Bình Hưng Hòa B',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000018-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000018-0000-0000-0000-000000000006', 'D0000018-0000-0000-0000-000000000000', 'Phường Bình Trị Đông', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000018-0000-0000-0000-000000000000',
    name = 'Phường Bình Trị Đông',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000018-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000018-0000-0000-0000-000000000007', 'D0000018-0000-0000-0000-000000000000', 'Phường Bình Trị Đông A', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000018-0000-0000-0000-000000000000',
    name = 'Phường Bình Trị Đông A',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000018-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000018-0000-0000-0000-000000000008', 'D0000018-0000-0000-0000-000000000000', 'Phường Bình Trị Đông B', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000018-0000-0000-0000-000000000000',
    name = 'Phường Bình Trị Đông B',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000018-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000018-0000-0000-0000-000000000009', 'D0000018-0000-0000-0000-000000000000', 'Phường Tân Tạo', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000018-0000-0000-0000-000000000000',
    name = 'Phường Tân Tạo',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000018-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000018-0000-0000-0000-000000000010', 'D0000018-0000-0000-0000-000000000000', 'Phường Tân Tạo A', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000018-0000-0000-0000-000000000000',
    name = 'Phường Tân Tạo A',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000018-0000-0000-0000-000000000010';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Thành phố Thủ Đức', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Thành phố Thủ Đức',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000000';

-- Ward level for Thành phố Thủ Đức
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000001', 'D0000009-0000-0000-0000-000000000000', 'Phường An Khánh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường An Khánh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000002', 'D0000009-0000-0000-0000-000000000000', 'Phường An Lợi Đông', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường An Lợi Đông',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000003', 'D0000009-0000-0000-0000-000000000000', 'Phường An Phú', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường An Phú',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000004', 'D0000009-0000-0000-0000-000000000000', 'Phường Bình Chiểu', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Bình Chiểu',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000005', 'D0000009-0000-0000-0000-000000000000', 'Phường Bình Thọ', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Bình Thọ',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000006', 'D0000009-0000-0000-0000-000000000000', 'Phường Bình Trưng Đông', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Bình Trưng Đông',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000007', 'D0000009-0000-0000-0000-000000000000', 'Phường Bình Trưng Tây', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Bình Trưng Tây',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000008', 'D0000009-0000-0000-0000-000000000000', 'Phường Cát Lái', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Cát Lái',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000009', 'D0000009-0000-0000-0000-000000000000', 'Phường Hiệp Bình Chánh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Hiệp Bình Chánh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000010', 'D0000009-0000-0000-0000-000000000000', 'Phường Hiệp Bình Phước', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Hiệp Bình Phước',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000011', 'D0000009-0000-0000-0000-000000000000', 'Phường Hiệp Phú', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Hiệp Phú',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000012', 'D0000009-0000-0000-0000-000000000000', 'Phường Linh Chiểu', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Linh Chiểu',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000012';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000013', 'D0000009-0000-0000-0000-000000000000', 'Phường Linh Đông', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Linh Đông',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000013';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000014', 'D0000009-0000-0000-0000-000000000000', 'Phường Linh Tây', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Linh Tây',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000014';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000015', 'D0000009-0000-0000-0000-000000000000', 'Phường Linh Trung', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Linh Trung',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000015';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000016', 'D0000009-0000-0000-0000-000000000000', 'Phường Linh Xuân', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Linh Xuân',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000016';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000017', 'D0000009-0000-0000-0000-000000000000', 'Phường Long Bình', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Long Bình',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000017';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000018', 'D0000009-0000-0000-0000-000000000000', 'Phường Long Phước', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Long Phước',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000018';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000019', 'D0000009-0000-0000-0000-000000000000', 'Phường Long Thạnh Mỹ', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Long Thạnh Mỹ',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000019';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000020', 'D0000009-0000-0000-0000-000000000000', 'Phường Long Trường', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Long Trường',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000020';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000021', 'D0000009-0000-0000-0000-000000000000', 'Phường Phú Hữu', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Phú Hữu',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000021';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000022', 'D0000009-0000-0000-0000-000000000000', 'Phường Phước Bình', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Phước Bình',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000022';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000023', 'D0000009-0000-0000-0000-000000000000', 'Phường Phước Long A', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Phước Long A',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000023';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000024', 'D0000009-0000-0000-0000-000000000000', 'Phường Phước Long B', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Phước Long B',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000024';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000025', 'D0000009-0000-0000-0000-000000000000', 'Phường Tam Bình', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Tam Bình',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000025';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000026', 'D0000009-0000-0000-0000-000000000000', 'Phường Tam Phú', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Tam Phú',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000026';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000027', 'D0000009-0000-0000-0000-000000000000', 'Phường Tăng Nhơn Phú A', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Tăng Nhơn Phú A',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000027';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000028', 'D0000009-0000-0000-0000-000000000000', 'Phường Tăng Nhơn Phú B', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Tăng Nhơn Phú B',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000028';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000029', 'D0000009-0000-0000-0000-000000000000', 'Phường Thạnh Mỹ Lợi', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Thạnh Mỹ Lợi',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000029';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000030', 'D0000009-0000-0000-0000-000000000000', 'Phường Thảo Điền', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Thảo Điền',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000030';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000031', 'D0000009-0000-0000-0000-000000000000', 'Phường Thủ Thiêm', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Thủ Thiêm',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000031';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000032', 'D0000009-0000-0000-0000-000000000000', 'Phường Trường Thạnh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Trường Thạnh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000032';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000033', 'D0000009-0000-0000-0000-000000000000', 'Phường Trường Thọ', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Trường Thọ',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000033';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000009-0000-0000-0000-000000000034', 'D0000009-0000-0000-0000-000000000000', 'Phường Yên Bình', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000009-0000-0000-0000-000000000000',
    name = 'Phường Yên Bình',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000009-0000-0000-0000-000000000034';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Huyện Bình Chánh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Huyện Bình Chánh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000000';

-- Ward level for Huyện Bình Chánh
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000001', 'D0000019-0000-0000-0000-000000000000', 'Thị trấn Tân Túc', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Thị trấn Tân Túc',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000002', 'D0000019-0000-0000-0000-000000000000', 'Xã An Phú Tây', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã An Phú Tây',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000003', 'D0000019-0000-0000-0000-000000000000', 'Xã Bình Chánh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Bình Chánh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000004', 'D0000019-0000-0000-0000-000000000000', 'Xã Bình Hưng', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Bình Hưng',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000005', 'D0000019-0000-0000-0000-000000000000', 'Xã Bình Lợi', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Bình Lợi',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000006', 'D0000019-0000-0000-0000-000000000000', 'Xã Đa Phước', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Đa Phước',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000007', 'D0000019-0000-0000-0000-000000000000', 'Xã Hưng Long', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Hưng Long',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000008', 'D0000019-0000-0000-0000-000000000000', 'Xã Lê Minh Xuân', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Lê Minh Xuân',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000009', 'D0000019-0000-0000-0000-000000000000', 'Xã Phạm Văn Hai', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Phạm Văn Hai',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000010', 'D0000019-0000-0000-0000-000000000000', 'Xã Phong Phú', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Phong Phú',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000011', 'D0000019-0000-0000-0000-000000000000', 'Xã Quy Đức', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Quy Đức',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000012', 'D0000019-0000-0000-0000-000000000000', 'Xã Tân Kiên', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Tân Kiên',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000012';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000013', 'D0000019-0000-0000-0000-000000000000', 'Xã Tân Nhựt', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Tân Nhựt',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000013';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000014', 'D0000019-0000-0000-0000-000000000000', 'Xã Tân Quý Tây', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Tân Quý Tây',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000014';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000015', 'D0000019-0000-0000-0000-000000000000', 'Xã Vĩnh Lộc A', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Vĩnh Lộc A',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000015';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000019-0000-0000-0000-000000000016', 'D0000019-0000-0000-0000-000000000000', 'Xã Vĩnh Lộc B', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000019-0000-0000-0000-000000000000',
    name = 'Xã Vĩnh Lộc B',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000019-0000-0000-0000-000000000016';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000020-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Huyện Hóc Môn', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Huyện Hóc Môn',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000020-0000-0000-0000-000000000000';

-- Ward level for Huyện Hóc Môn
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000020-0000-0000-0000-000000000001', 'D0000020-0000-0000-0000-000000000000', 'Thị trấn Hóc Môn', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000020-0000-0000-0000-000000000000',
    name = 'Thị trấn Hóc Môn',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000020-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000020-0000-0000-0000-000000000002', 'D0000020-0000-0000-0000-000000000000', 'Xã Bà Điểm', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000020-0000-0000-0000-000000000000',
    name = 'Xã Bà Điểm',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000020-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000020-0000-0000-0000-000000000003', 'D0000020-0000-0000-0000-000000000000', 'Xã Đông Thạnh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000020-0000-0000-0000-000000000000',
    name = 'Xã Đông Thạnh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000020-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000020-0000-0000-0000-000000000004', 'D0000020-0000-0000-0000-000000000000', 'Xã Nhị Bình', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000020-0000-0000-0000-000000000000',
    name = 'Xã Nhị Bình',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000020-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000020-0000-0000-0000-000000000005', 'D0000020-0000-0000-0000-000000000000', 'Xã Tân Hiệp', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000020-0000-0000-0000-000000000000',
    name = 'Xã Tân Hiệp',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000020-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000020-0000-0000-0000-000000000006', 'D0000020-0000-0000-0000-000000000000', 'Xã Tân Thới Nhì', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000020-0000-0000-0000-000000000000',
    name = 'Xã Tân Thới Nhì',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000020-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000020-0000-0000-0000-000000000007', 'D0000020-0000-0000-0000-000000000000', 'Xã Tân Xuân', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000020-0000-0000-0000-000000000000',
    name = 'Xã Tân Xuân',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000020-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000020-0000-0000-0000-000000000008', 'D0000020-0000-0000-0000-000000000000', 'Xã Thới Tam Thôn', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000020-0000-0000-0000-000000000000',
    name = 'Xã Thới Tam Thôn',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000020-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000020-0000-0000-0000-000000000009', 'D0000020-0000-0000-0000-000000000000', 'Xã Trung Chánh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000020-0000-0000-0000-000000000000',
    name = 'Xã Trung Chánh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000020-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000020-0000-0000-0000-000000000010', 'D0000020-0000-0000-0000-000000000000', 'Xã Xuân Thới Đông', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000020-0000-0000-0000-000000000000',
    name = 'Xã Xuân Thới Đông',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000020-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000020-0000-0000-0000-000000000011', 'D0000020-0000-0000-0000-000000000000', 'Xã Xuân Thới Sơn', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000020-0000-0000-0000-000000000000',
    name = 'Xã Xuân Thới Sơn',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000020-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000020-0000-0000-0000-000000000012', 'D0000020-0000-0000-0000-000000000000', 'Xã Xuân Thới Thượng', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000020-0000-0000-0000-000000000000',
    name = 'Xã Xuân Thới Thượng',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000020-0000-0000-0000-000000000012';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Huyện Củ Chi', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Huyện Củ Chi',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000000';

-- Ward level for Huyện Củ Chi
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000001', 'D0000021-0000-0000-0000-000000000000', 'Thị trấn Củ Chi', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Thị trấn Củ Chi',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000002', 'D0000021-0000-0000-0000-000000000000', 'Xã An Nhơn Tây', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã An Nhơn Tây',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000003', 'D0000021-0000-0000-0000-000000000000', 'Xã An Phú', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã An Phú',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000004', 'D0000021-0000-0000-0000-000000000000', 'Xã Bình Mỹ', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Bình Mỹ',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000005', 'D0000021-0000-0000-0000-000000000000', 'Xã Hòa Phú', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Hòa Phú',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000006', 'D0000021-0000-0000-0000-000000000000', 'Xã Nhuận Đức', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Nhuận Đức',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000007', 'D0000021-0000-0000-0000-000000000000', 'Xã Phạm Văn Cội', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Phạm Văn Cội',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000008', 'D0000021-0000-0000-0000-000000000000', 'Xã Phú Hòa Đông', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Phú Hòa Đông',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000008';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000009', 'D0000021-0000-0000-0000-000000000000', 'Xã Phú Mỹ Hưng', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Phú Mỹ Hưng',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000009';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000010', 'D0000021-0000-0000-0000-000000000000', 'Xã Phước Hiệp', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Phước Hiệp',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000010';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000011', 'D0000021-0000-0000-0000-000000000000', 'Xã Phước Thạnh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Phước Thạnh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000011';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000012', 'D0000021-0000-0000-0000-000000000000', 'Xã Phước Vĩnh An', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Phước Vĩnh An',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000012';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000013', 'D0000021-0000-0000-0000-000000000000', 'Xã Tân An Hội', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Tân An Hội',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000013';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000014', 'D0000021-0000-0000-0000-000000000000', 'Xã Tân Phú Trung', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Tân Phú Trung',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000014';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000015', 'D0000021-0000-0000-0000-000000000000', 'Xã Tân Thạnh Đông', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Tân Thạnh Đông',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000015';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000016', 'D0000021-0000-0000-0000-000000000000', 'Xã Tân Thạnh Tây', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Tân Thạnh Tây',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000016';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000017', 'D0000021-0000-0000-0000-000000000000', 'Xã Tân Thông Hội', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Tân Thông Hội',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000017';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000018', 'D0000021-0000-0000-0000-000000000000', 'Xã Thái Mỹ', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Thái Mỹ',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000018';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000019', 'D0000021-0000-0000-0000-000000000000', 'Xã Trung An', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Trung An',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000019';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000020', 'D0000021-0000-0000-0000-000000000000', 'Xã Trung Lập Hạ', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Trung Lập Hạ',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000020';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000021-0000-0000-0000-000000000021', 'D0000021-0000-0000-0000-000000000000', 'Xã Trung Lập Thượng', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000021-0000-0000-0000-000000000000',
    name = 'Xã Trung Lập Thượng',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000021-0000-0000-0000-000000000021';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000022-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Huyện Nhà Bè', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Huyện Nhà Bè',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000022-0000-0000-0000-000000000000';

-- Ward level for Huyện Nhà Bè
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000022-0000-0000-0000-000000000001', 'D0000022-0000-0000-0000-000000000000', 'Thị trấn Nhà Bè', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000022-0000-0000-0000-000000000000',
    name = 'Thị trấn Nhà Bè',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000022-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000022-0000-0000-0000-000000000002', 'D0000022-0000-0000-0000-000000000000', 'Xã Hiệp Phước', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000022-0000-0000-0000-000000000000',
    name = 'Xã Hiệp Phước',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000022-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000022-0000-0000-0000-000000000003', 'D0000022-0000-0000-0000-000000000000', 'Xã Long Thới', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000022-0000-0000-0000-000000000000',
    name = 'Xã Long Thới',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000022-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000022-0000-0000-0000-000000000004', 'D0000022-0000-0000-0000-000000000000', 'Xã Nhơn Đức', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000022-0000-0000-0000-000000000000',
    name = 'Xã Nhơn Đức',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000022-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000022-0000-0000-0000-000000000005', 'D0000022-0000-0000-0000-000000000000', 'Xã Phú Xuân', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000022-0000-0000-0000-000000000000',
    name = 'Xã Phú Xuân',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000022-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000022-0000-0000-0000-000000000006', 'D0000022-0000-0000-0000-000000000000', 'Xã Phước Kiển', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000022-0000-0000-0000-000000000000',
    name = 'Xã Phước Kiển',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000022-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000022-0000-0000-0000-000000000007', 'D0000022-0000-0000-0000-000000000000', 'Xã Phước Lộc', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000022-0000-0000-0000-000000000000',
    name = 'Xã Phước Lộc',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000022-0000-0000-0000-000000000007';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000023-0000-0000-0000-000000000000', '11111111-1111-1111-1111-111111111111', 'Huyện Cần Giờ', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Huyện Cần Giờ',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000023-0000-0000-0000-000000000000';

-- Ward level for Huyện Cần Giờ
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000023-0000-0000-0000-000000000001', 'D0000023-0000-0000-0000-000000000000', 'Thị trấn Cần Thạnh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000023-0000-0000-0000-000000000000',
    name = 'Thị trấn Cần Thạnh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000023-0000-0000-0000-000000000001';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000023-0000-0000-0000-000000000002', 'D0000023-0000-0000-0000-000000000000', 'Xã An Thới Đông', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000023-0000-0000-0000-000000000000',
    name = 'Xã An Thới Đông',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000023-0000-0000-0000-000000000002';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000023-0000-0000-0000-000000000003', 'D0000023-0000-0000-0000-000000000000', 'Xã Bình Khánh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000023-0000-0000-0000-000000000000',
    name = 'Xã Bình Khánh',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000023-0000-0000-0000-000000000003';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000023-0000-0000-0000-000000000004', 'D0000023-0000-0000-0000-000000000000', 'Xã Long Hòa', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000023-0000-0000-0000-000000000000',
    name = 'Xã Long Hòa',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000023-0000-0000-0000-000000000004';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000023-0000-0000-0000-000000000005', 'D0000023-0000-0000-0000-000000000000', 'Xã Lý Nhơn', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000023-0000-0000-0000-000000000000',
    name = 'Xã Lý Nhơn',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000023-0000-0000-0000-000000000005';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000023-0000-0000-0000-000000000006', 'D0000023-0000-0000-0000-000000000000', 'Xã Tam Thôn Hiệp', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000023-0000-0000-0000-000000000000',
    name = 'Xã Tam Thôn Hiệp',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000023-0000-0000-0000-000000000006';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('D0000023-0000-0000-0000-000000000007', 'D0000023-0000-0000-0000-000000000000', 'Xã Thạnh An', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE areas
SET parent_id = 'D0000023-0000-0000-0000-000000000000',
    name = 'Xã Thạnh An',
    is_active = 1,
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = CURRENT_TIMESTAMP
WHERE id = 'D0000023-0000-0000-0000-000000000007';
