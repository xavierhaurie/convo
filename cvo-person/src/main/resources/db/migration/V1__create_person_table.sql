CREATE DATABASE IF NOT EXISTS person;

CREATE TABLE person (
                        id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                        is_npc BOOLEAN NOT NULL DEFAULT TRUE,
                        name  VARCHAR(255),
                        description VARCHAR(1000),
                        book_file_uuid VARCHAR(36)
);
