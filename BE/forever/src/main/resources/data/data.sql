-- Member 테이블
INSERT INTO member_tb (nickname, email, major, school, refresh_token, kakao_access_token,
                       available_tokens, is_agreed_policy, effective_date_policy,
                       is_agreed_terms, effective_date_terms,
                       is_deleted, deleted_at,
                       created_at, updated_at)
VALUES ('근성', 'r@naver.com', '소프트웨어공학과', '전남대학교',
        'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibWVtYmVySWQiOjEsImlhdCI6MTc0MzYzNDU4NCwiZXhwIjoxNzQ0MjM5Mzg0fQ.OESAIxVmY-q8VdMVrWJPLdKO2u5h4ryafutI98Aq7FA',
        'sample-kakao-access-token-1',
        10, true, '2025-04-03', true, '2025-04-03',
        false, NULL,
        NOW(), NOW()),
       ('minju', 'minju_kwak@naver.com', 'Software Engineering', '연세대학교',
        NULL,
        NULL,
        10, true, '2025-04-03', true, '2025-04-03',
        false, NULL,
        NOW(), NOW()),
       ('jisu', 'jisu@example.com', 'Computer Science', '고려대학교',
        NULL,
        NULL,
        10, true, '2025-04-03', true, '2025-04-03',
        false, NULL,
        NOW(), NOW());
-- Folder 테이블 (parent_folder_id는 null이면 루트 폴더)
INSERT INTO folder_tb (name, created_by, created_at, updated_at)
VALUES ('Reports', 1, NOW(), NOW()),
       ('Projects', 1, NOW(), NOW()),
       ('Subfolder A', 1, NOW(), NOW());


-- Document 테이블 (각 파일은 Member와 Folder에 연결됨)
INSERT INTO document_tb (title, summary, member_id, folder_id, created_at, updated_at)
VALUES ('deep_learning_notes.pdf', '딥러닝 노트 정리본', 1, 1, NOW(), NOW()),
       ('database_cheatsheet.md', 'DB 핵심 개념 요약본', 1, 2, NOW(), NOW()),
       ('springboot_guide.docx', 'Spring Boot 입문 가이드', 1, 2, NOW(), NOW()),
       ('project_plan.xlsx', '2025 프로젝트 계획안', 1, null, NOW(), NOW()),
       ('interview_qna.txt', '면접 질문과 답변 정리', 2, null, NOW(), NOW()),
       ('research_summary.pdf', '연구 논문 요약 정리', 1, 1, NOW(), NOW()),
       ('network_basics.ppt', '컴퓨터 네트워크 기본 개념', 1, null, NOW(), NOW());


-- Item 테이블 (파일과 폴더 모두 포함)
INSERT INTO item_tb (type, ref_id, folder_id, order_value)
VALUES
-- 폴더 아이템 (최상위 폴더로)
('FOLDER', 1, null, 1000),
('FOLDER', 2, null, 200),
-- 하위 폴더
-- (3, 'FOLDER', 3, 1, 1),

-- 파일 아이템
('FILE', 1, 1, 25),
('FILE', 2, 2, 309),
('FILE', 3, 2, 400),
('FILE', 4, null, 111),
('FILE', 5, null, 35),
('FILE', 6, 1, 55),
('FILE', 7, null, 1000);


-- Question 테이블
INSERT INTO question_tb (content, document_id, created_at, updated_at)
VALUES ('What is supervised learning?', 1, NOW(), NOW()),
       ('Explain backpropagation.', 1, NOW(), NOW()),
       ('What is overfitting?', 2, NOW(), NOW()),
       ('How does dropout help prevent overfitting?', 1, NOW(), NOW()),
       ('Describe the difference between TCP and UDP.', 2, NOW(), NOW()),
       ('What is normalization in databases?', 5, NOW(), NOW()),
       ('Explain the purpose of Spring Boot annotations.', 6, NOW(), NOW()),
       ('What is the vanishing gradient problem?', 4, NOW(), NOW()),
       ('Describe the CAP theorem.', 5, NOW(), NOW()),
       ('What is the difference between classification and regression?', 4, NOW(), NOW()),
       ('How do REST APIs differ from GraphQL?', 6, NOW(), NOW()),
       ('Why is indexing important in relational databases?', 5, NOW(), NOW());


-- Answer 테이블 (Question과 1:1 연결)
INSERT INTO answer_tb (content, question_id, created_at, updated_at)
VALUES ('Supervised learning uses labeled data to train models.', 1, NOW(), NOW()),
       ('Backpropagation is used to compute gradients and update weights.', 2, NOW(), NOW()),
       ('Overfitting occurs when a model learns noise instead of the signal.', 3, NOW(), NOW()),
       ('Dropout randomly disables neurons during training to reduce overfitting.', 4, NOW(), NOW()),
       ('TCP is connection-oriented while UDP is connectionless.', 5, NOW(), NOW()),
       ('Normalization organizes data to reduce redundancy in databases.', 6, NOW(), NOW()),
       ('Spring Boot annotations configure beans, request mappings, etc.', 7, NOW(), NOW()),
       ('Vanishing gradient makes training deep networks difficult.', 8, NOW(), NOW()),
       ('CAP theorem says consistency, availability, partition tolerance can’t all be achieved.', 9, NOW(), NOW()),
       ('Classification assigns labels, regression predicts continuous values.', 10, NOW(), NOW()),
       ('REST uses fixed routes, GraphQL allows flexible querying.', 11, NOW(), NOW()),
       ('Indexing speeds up query performance by allowing fast lookups.', 12, NOW(), NOW());


INSERT INTO app_info_tb (latest_version, store_url)
VALUES ('0.1.1', 'https://play.google.com/store/apps/details?id=com.forever.summarysnap');