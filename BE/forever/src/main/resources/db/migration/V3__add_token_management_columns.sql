-- 토큰 관리 컬럼 추가
ALTER TABLE member_tb 
ADD COLUMN total_usage_count INT DEFAULT 0 NOT NULL COMMENT '총 토큰 사용 횟수',
ADD COLUMN last_token_refresh_date DATE DEFAULT (CURRENT_DATE) NOT NULL COMMENT '마지막 토큰 충전 날짜';

-- 기존 회원들의 availableTokens를 3으로 업데이트
UPDATE member_tb 
SET available_tokens = 3 
WHERE available_tokens != 3;

-- 인덱스 추가 (성능 최적화)
CREATE INDEX idx_member_last_token_refresh_date ON member_tb(last_token_refresh_date);
CREATE INDEX idx_member_available_tokens ON member_tb(available_tokens);
