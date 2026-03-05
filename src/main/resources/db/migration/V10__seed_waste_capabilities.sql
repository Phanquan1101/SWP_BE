-- Seed global waste capabilities for default categories (idempotent)
-- Scope: PAPER, PLASTIC, ORGANIC, HAZARDOUS

INSERT INTO waste_capabilities (id, waste_category_id, daily_capacity_kg, is_accepting)
SELECT 'c1111111-1111-1111-1111-111111111111', wc.id, 500.000, 1
FROM waste_categories wc
WHERE wc.code = 'PAPER'
ON DUPLICATE KEY UPDATE
    daily_capacity_kg = 500.000,
    is_accepting = 1,
    updated_at = CURRENT_TIMESTAMP;

INSERT INTO waste_capabilities (id, waste_category_id, daily_capacity_kg, is_accepting)
SELECT 'c2222222-2222-2222-2222-222222222222', wc.id, 800.000, 1
FROM waste_categories wc
WHERE wc.code = 'PLASTIC'
ON DUPLICATE KEY UPDATE
    daily_capacity_kg = 800.000,
    is_accepting = 1,
    updated_at = CURRENT_TIMESTAMP;

INSERT INTO waste_capabilities (id, waste_category_id, daily_capacity_kg, is_accepting)
SELECT 'c3333333-3333-3333-3333-333333333333', wc.id, 1200.000, 1
FROM waste_categories wc
WHERE wc.code = 'ORGANIC'
ON DUPLICATE KEY UPDATE
    daily_capacity_kg = 1200.000,
    is_accepting = 1,
    updated_at = CURRENT_TIMESTAMP;

INSERT INTO waste_capabilities (id, waste_category_id, daily_capacity_kg, is_accepting)
SELECT 'c4444444-4444-4444-4444-444444444444', wc.id, 100.000, 1
FROM waste_categories wc
WHERE wc.code = 'HAZARDOUS'
ON DUPLICATE KEY UPDATE
    daily_capacity_kg = 100.000,
    is_accepting = 1,
    updated_at = CURRENT_TIMESTAMP;
