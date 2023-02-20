-- 트리거 목록 조회
SHOW TRIGGERS;

-- 새로운 배지 등록 시 자동으로 my_badge 테이블에 기존 유저가 등록되는 트리거
CREATE TRIGGER NEW_BADGE
    AFTER INSERT ON badge FOR EACH ROW
    INSERT INTO my_badge(user_id, badge_id, status_info, on_profile, earned_at)
    SELECT a.id, NEW.id, 'ONGOING', true, null
    FROM user a;

-- 새로운 유저 등록 시 자동으로 my_badge 테이블에 기존 배지가 등록되는 트리거
CREATE TRIGGER NEW_USER
    AFTER INSERT ON user FOR EACH ROW
    INSERT INTO my_badge(user_id, badge_id, status_info, on_profile, earned_at)
    SELECT NEW.id, b.id, 'ONGOING', true, null
    FROM badge b;

-- 새로운 유저 등록 시 자동으로 github_repo 테이블에 repo_name이 등록되는 트리거
CREATE TRIGGER INSERT_REPO_DEFAULT
AFTER INSERT ON user FOR EACH ROW
INSERT INTO github_repo(user_id, repo_name, repo_checked)
SELECT NEW.id, '', false;