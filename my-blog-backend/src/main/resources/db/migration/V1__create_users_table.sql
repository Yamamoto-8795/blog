CREATE TABLE users  (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mail VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    bio TEXT,
    profile_image_url VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);