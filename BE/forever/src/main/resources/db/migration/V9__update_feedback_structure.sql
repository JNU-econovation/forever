-- 1. 기존 content 컬럼 제거
ALTER TABLE feedback_tb
DROP COLUMN content;

-- 2. rating 컬럼 범위 제약 조건 추가
ALTER TABLE feedback_tb
    ADD CONSTRAINT chk_rating CHECK (rating >= 1 AND rating <= 10);

-- 3. 피드백 내용 테이블 생성 (정규화 구조)
CREATE TABLE IF NOT EXISTS feedback_content_tb (
                                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                   feedback_id BIGINT NOT NULL,
                                                   content VARCHAR(500) NOT NULL COMMENT '피드백 내용',
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_feedback_content_feedback
    FOREIGN KEY (feedback_id) REFERENCES feedback_tb(id) ON DELETE CASCADE
    );
