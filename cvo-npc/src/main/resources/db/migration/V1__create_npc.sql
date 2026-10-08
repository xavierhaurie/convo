CREATE DATABASE IF NOT EXISTS npc;

CREATE TABLE npc (
                         id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name  VARCHAR(255),
                         description VARCHAR(1000),
                         book_url VARCHAR(1000)
);

INSERT INTO npc (name, description, book_url) VALUES
                                             ('Alice', 'Alice enjoys painting landscapes with watercolors', NULL),
                                             ('Bob',   'Bob likes to paint portraits with oil', NULL);
