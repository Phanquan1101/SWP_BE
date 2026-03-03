-- Seed HCM area tree for MVP dropdown (idempotent)

-- Root
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('11111111-1111-1111-1111-111111111111', NULL, 'TP.HCM', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
UPDATE areas
SET parent_id = NULL,
    name = 'TP.HCM',
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE id = '11111111-1111-1111-1111-111111111111';

-- District level
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('22222222-2222-2222-2222-222222222222', '11111111-1111-1111-1111-111111111111', 'Quan 1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quan 1',
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE id = '22222222-2222-2222-2222-222222222222';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('70000000-0000-0000-0000-000000000007', '11111111-1111-1111-1111-111111111111', 'Quan 7', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'Quan 7',
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE id = '70000000-0000-0000-0000-000000000007';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('80000000-0000-0000-0000-000000000008', '11111111-1111-1111-1111-111111111111', 'TP Thu Duc', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
UPDATE areas
SET parent_id = '11111111-1111-1111-1111-111111111111',
    name = 'TP Thu Duc',
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE id = '80000000-0000-0000-0000-000000000008';

-- Ward level for Quan 1
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('33333333-3333-3333-3333-333333333333', '22222222-2222-2222-2222-222222222222', 'Phuong Ben Nghe', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
UPDATE areas
SET parent_id = '22222222-2222-2222-2222-222222222222',
    name = 'Phuong Ben Nghe',
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE id = '33333333-3333-3333-3333-333333333333';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('33444444-3333-3333-3333-333333333334', '22222222-2222-2222-2222-222222222222', 'Phuong Ben Thanh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
UPDATE areas
SET parent_id = '22222222-2222-2222-2222-222222222222',
    name = 'Phuong Ben Thanh',
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE id = '33444444-3333-3333-3333-333333333334';

-- Ward level for Quan 7
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('70000000-0000-0000-0000-000000000071', '70000000-0000-0000-0000-000000000007', 'Phuong Tan Phu', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
UPDATE areas
SET parent_id = '70000000-0000-0000-0000-000000000007',
    name = 'Phuong Tan Phu',
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE id = '70000000-0000-0000-0000-000000000071';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('70000000-0000-0000-0000-000000000072', '70000000-0000-0000-0000-000000000007', 'Phuong Tan Hung', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
UPDATE areas
SET parent_id = '70000000-0000-0000-0000-000000000007',
    name = 'Phuong Tan Hung',
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE id = '70000000-0000-0000-0000-000000000072';

-- Ward level for TP Thu Duc
INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('80000000-0000-0000-0000-000000000081', '80000000-0000-0000-0000-000000000008', 'Phuong Linh Trung', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
UPDATE areas
SET parent_id = '80000000-0000-0000-0000-000000000008',
    name = 'Phuong Linh Trung',
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE id = '80000000-0000-0000-0000-000000000081';

INSERT IGNORE INTO areas (id, parent_id, name, is_active, created_at, updated_at)
VALUES ('80000000-0000-0000-0000-000000000082', '80000000-0000-0000-0000-000000000008', 'Phuong Hiep Binh Chanh', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
UPDATE areas
SET parent_id = '80000000-0000-0000-0000-000000000008',
    name = 'Phuong Hiep Binh Chanh',
    is_active = 1,
    updated_at = CURRENT_TIMESTAMP
WHERE id = '80000000-0000-0000-0000-000000000082';
