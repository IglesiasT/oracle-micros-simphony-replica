INSERT INTO roles (authority)
VALUES
    ('USER'),
    ('EDITOR'),
    ('ADMIN')
ON CONFLICT (authority) DO NOTHING;
