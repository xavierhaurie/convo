CREATE DATABASE IF NOT EXISTS converse;

CREATE TABLE utterance (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         reply_to BIGINT,
                         content TEXT,
                         from_person_id BIGINT,
                         to_person_id BIGINT
);
