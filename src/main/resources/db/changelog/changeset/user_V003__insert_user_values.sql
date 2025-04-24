INSERT INTO users (
    name, surname, username, password, email, phone, about_me,
    city, country_id, experience, pic_url, uploaded_pic_url,
    created_by, updated_by, version)

VALUES (
    'Alice', 'Klim', 'alicek', 'pass123', 'alice@example.com', '390123456789',
    'Java dev', 'Rome', '10bfb1c8-1a15-4410-be88-7245ca57e7cb', 5, NULL, NULL, 'admin', 'admin', 1),
    ('Bob', 'Smith', 'bobsmith', 'pass456', 'bob@example.com', '+440123456789',
    'Backend dev', 'London', 'dea71b30-9b20-4afb-aeed-217d41dd0517', 3, NULL, NULL, 'admin', 'admin', 1);