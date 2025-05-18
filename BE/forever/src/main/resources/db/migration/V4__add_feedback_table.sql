-- 피드백 테이블 생성
CREATE TABLE feedback_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    position VARCHAR(255) NOT NULL,
    content TEXT,
    rating INT NOT NULL,
    processed BOOLEAN NOT NULL DEFAULT FALSE,
    member_id BIGINT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    
    CONSTRAINT fk_feedback_member
    FOREIGN KEY (member_id)
    REFERENCES member_tb(id)
    ON DELETE SET NULL
);

-- 인덱스 추가
CREATE INDEX idx_feedback_position ON feedback_tb(position);
CREATE INDEX idx_feedback_processed ON feedback_tb(processed);
