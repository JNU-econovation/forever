-- 문서 테이블에 소프트 삭제 필드 추가
ALTER TABLE document_tb
    ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN deleted_at DATETIME(6);

-- 폴더 테이블에 소프트 삭제 필드 추가
ALTER TABLE folder_tb
    ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN deleted_at DATETIME(6);

-- 기존 데이터 초기화
UPDATE document_tb SET is_deleted = FALSE WHERE is_deleted IS NULL;
UPDATE folder_tb SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- 인덱스 추가
CREATE INDEX idx_document_is_deleted ON document_tb(is_deleted);
CREATE INDEX idx_folder_is_deleted ON folder_tb(is_deleted);
