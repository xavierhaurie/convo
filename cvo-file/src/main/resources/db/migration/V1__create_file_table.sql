CREATE DATABASE IF NOT EXISTS file_info;

CREATE TABLE cvo_file (
                         id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name  VARCHAR(255),
                         description VARCHAR(1000),
                         uuid VARCHAR(36)
);
