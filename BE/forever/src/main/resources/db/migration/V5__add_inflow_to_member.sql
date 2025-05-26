-- Add inflow column to member_tb
ALTER TABLE member_tb ADD COLUMN inflow VARCHAR(1024);

-- Add comment to explain the column
ALTER TABLE member_tb MODIFY COLUMN inflow VARCHAR(1024) COMMENT '유입경로 정보 (콤마로 구분된 문자열)';
