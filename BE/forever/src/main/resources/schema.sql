
CREATE TABLE document_tb (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             title VARCHAR(45) NOT NULL,
                             summary TEXT
);


CREATE TABLE question_tb (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             content VARCHAR(45) NOT NULL,
                             document_id BIGINT NOT NULL,
                             CONSTRAINT fk_document FOREIGN KEY (document_id) REFERENCES document_tb(id)
);

CREATE TABLE answer_tb (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           content VARCHAR(45) NOT NULL,
                           question_id BIGINT NOT NULL,
                           CONSTRAINT fk_question FOREIGN KEY (question_id) REFERENCES question_tb(id)
);


