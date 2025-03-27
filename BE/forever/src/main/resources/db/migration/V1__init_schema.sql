CREATE DATABASE IF NOT EXISTS `forever`;
USE `forever`;

CREATE TABLE IF NOT EXISTS member_tb (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    nickname VARCHAR(255),
                                    email VARCHAR(255) NOT NULL UNIQUE,
                                    major VARCHAR(255),
                                    refresh_token VARCHAR(512),
                                    agreed_to_privacy BOOLEAN NOT NULL,
                                    created_at DATETIME NOT NULL,
                                    updated_at DATETIME NOT NULL
    );


CREATE TABLE IF NOT EXISTS document_tb (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             title VARCHAR(255) NOT NULL,
                             summary TEXT,
                             member_id BIGINT NOT NULL,
                             created_at DATETIME NOT NULL,
                             updated_at DATETIME NOT NULL,
                            CONSTRAINT fk_document_member FOREIGN KEY (member_id)
                            REFERENCES member_tb(id)
                            ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS question_tb (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             content TEXT NOT NULL,
                             document_id BIGINT NOT NULL,
                             created_at DATETIME NOT NULL,
                             updated_at DATETIME NOT NULL,
                             CONSTRAINT fk_question_document FOREIGN KEY (document_id)
                                 REFERENCES document_tb(id)
                                 ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS answer_tb (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           content TEXT NOT NULL,
                           question_id BIGINT NOT NULL UNIQUE,
                           created_at DATETIME NOT NULL,
                           updated_at DATETIME NOT NULL,
                           CONSTRAINT fk_answer_question FOREIGN KEY (question_id)
                               REFERENCES question_tb(id)
                               ON DELETE CASCADE
);
