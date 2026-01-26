-- Password for all demo users: Password@123
-- BCrypt hash: $2b$10$6Qs0rR.lj9z/R5df/usaa.FAekYLDsvYySEYO8TU0cEdOjyG0W7rS

INSERT INTO roles (id, code, name, description) VALUES
(UUID(), 'ROLE_ADMIN', 'Admin', 'System administrator'),
(UUID(), 'ROLE_CITIZEN', 'Citizen', 'Citizen user'),
(UUID(), 'ROLE_ENTERPRISE_MANAGER', 'Enterprise Manager', 'Enterprise manager'),
(UUID(), 'ROLE_COLLECTOR', 'Collector', 'Waste collector');

INSERT INTO users (id, email, phone, password_hash, full_name, user_type, status, suspended_reason)
VALUES
(UUID(), 'admin@example.com', NULL, '$2b$10$6Qs0rR.lj9z/R5df/usaa.FAekYLDsvYySEYO8TU0cEdOjyG0W7rS', 'Admin User', 'ADMIN', 'ACTIVE', NULL),
(UUID(), 'citizen@example.com', NULL, '$2b$10$6Qs0rR.lj9z/R5df/usaa.FAekYLDsvYySEYO8TU0cEdOjyG0W7rS', 'Citizen User', 'CITIZEN', 'ACTIVE', NULL),
(UUID(), 'enterprise@example.com', NULL, '$2b$10$6Qs0rR.lj9z/R5df/usaa.FAekYLDsvYySEYO8TU0cEdOjyG0W7rS', 'Enterprise Manager', 'ENTERPRISE_MANAGER', 'ACTIVE', NULL),
(UUID(), 'collector@example.com', NULL, '$2b$10$6Qs0rR.lj9z/R5df/usaa.FAekYLDsvYySEYO8TU0cEdOjyG0W7rS', 'Collector User', 'COLLECTOR', 'ACTIVE', NULL);

INSERT INTO user_roles (id, user_id, role_id)
SELECT UUID(), u.id, r.id
FROM users u
JOIN roles r ON r.code = 'ROLE_ADMIN'
WHERE u.email = 'admin@example.com';

INSERT INTO user_roles (id, user_id, role_id)
SELECT UUID(), u.id, r.id
FROM users u
JOIN roles r ON r.code = 'ROLE_CITIZEN'
WHERE u.email = 'citizen@example.com';

INSERT INTO user_roles (id, user_id, role_id)
SELECT UUID(), u.id, r.id
FROM users u
JOIN roles r ON r.code = 'ROLE_ENTERPRISE_MANAGER'
WHERE u.email = 'enterprise@example.com';

INSERT INTO user_roles (id, user_id, role_id)
SELECT UUID(), u.id, r.id
FROM users u
JOIN roles r ON r.code = 'ROLE_COLLECTOR'
WHERE u.email = 'collector@example.com';
