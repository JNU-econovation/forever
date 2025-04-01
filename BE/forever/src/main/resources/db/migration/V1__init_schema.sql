CREATE DATABASE IF NOT EXISTS `forever`;
USE `forever`;

CREATE TABLE IF NOT EXISTS member_tb (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         nickname VARCHAR(255),
                                        email VARCHAR(255) NOT NULL UNIQUE,
                                        major VARCHAR(512),
                                        refresh_token VARCHAR(512),
                                        available_tokens INT DEFAULT 3,
                                        is_agreed_policy BOOLEAN NOT NULL DEFAULT TRUE,
                                        effective_date_policy DATE NOT NULL DEFAULT '2025-04-03',
                                        is_agreed_terms BOOLEAN NOT NULL DEFAULT TRUE,
                                        effective_date_terms DATE NOT NULL DEFAULT '2025-04-03',
                                        created_at DATETIME(6) NOT NULL,
                                        updated_at DATETIME(6) NOT NULL
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

CREATE TABLE IF NOT EXISTS verification_code_tb (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            email VARCHAR(255) NOT NULL,
                            code VARCHAR(255) NOT NULL,
                            expires_time DATETIME(6) NOT NULL,
                            created_at DATETIME NOT NULL,
                            updated_at DATETIME NOT NULL
    );
)
