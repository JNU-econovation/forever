-- Add marketing agreement fields to member_tb table (V7)
-- MySQL does not support "ADD COLUMN IF NOT EXISTS" syntax
-- Flyway ensures this migration runs only once per environment

ALTER TABLE member_tb 
ADD COLUMN marketing_agreement BOOLEAN DEFAULT FALSE COMMENT '마케팅 동의 여부',
ADD COLUMN marketing_agreement_date DATETIME COMMENT '마케팅 동의 유효날짜';

-- Update existing members with default values  
UPDATE member_tb 
SET marketing_agreement = FALSE, 
    marketing_agreement_date = NOW() 
WHERE marketing_agreement IS NULL;
