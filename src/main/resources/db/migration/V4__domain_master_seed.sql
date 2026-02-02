-- Seed areas
INSERT INTO areas (id, parent_id, name) VALUES
  ('11111111-1111-1111-1111-111111111111', NULL, 'Ho Chi Minh City'),
  ('22222222-2222-2222-2222-222222222222', '11111111-1111-1111-1111-111111111111', 'District 1'),
  ('33333333-3333-3333-3333-333333333333', '22222222-2222-2222-2222-222222222222', 'Ben Nghe Ward')
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Seed waste categories
INSERT INTO waste_categories (id, code, name) VALUES
  ('44444444-4444-4444-4444-444444444444', 'PAPER', 'Paper'),
  ('55555555-5555-5555-5555-555555555555', 'PLASTIC', 'Plastic'),
  ('66666666-6666-6666-6666-666666666666', 'ORGANIC', 'Organic'),
  ('77777777-7777-7777-7777-777777777777', 'HAZARDOUS', 'Hazardous')
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Seed demo enterprise
SET @ent_id = '88888888-8888-8888-8888-888888888888';
INSERT INTO enterprises (id, name, tax_code, address_text)
VALUES (@ent_id, 'Demo Recycling Enterprise', 'TAX-0001', '123 Demo Street, District 1, HCMC')
ON DUPLICATE KEY UPDATE name = VALUES(name), tax_code = VALUES(tax_code), address_text = VALUES(address_text);

-- Link enterprise manager user to enterprise and area
UPDATE users
SET enterprise_id = @ent_id,
    area_id = '33333333-3333-3333-3333-333333333333'
WHERE email = 'enterprise@example.com';

-- Seed enterprise capabilities (PLASTIC, PAPER)
INSERT INTO enterprise_waste_capabilities (id, enterprise_id, waste_category_id, daily_capacity_kg, is_accepting)
VALUES
  ('99999999-9999-9999-9999-999999999991', @ent_id, '55555555-5555-5555-5555-555555555555', 100.000, 1),
  ('99999999-9999-9999-9999-999999999992', @ent_id, '44444444-4444-4444-4444-444444444444', 100.000, 1)
ON DUPLICATE KEY UPDATE daily_capacity_kg = VALUES(daily_capacity_kg), is_accepting = VALUES(is_accepting);
