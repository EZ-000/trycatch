-- 이벤트 사용 설정 확인
SHOW VARIABLES LIKE 'event%';
-- 이벤트 사용 설정
SET GLOBAL event_scheduler = ON;
-- 이벤트 목록 조회
SELECT * FROM information_schema.events;

-- BETA DB와 PROD DB의 배지 아이디가 다르기 때문에 맞춰서 t.badge_id 수정 필요
-- Preview update로 확인하고 실행할 것
CREATE EVENT BADGE_1_GOLD_EARLY_BIRD
ON SCHEDULE EVERY 1 HOUR
STARTS NOW()
COMMENT '배지_1_골드_얼리버드'
DO
UPDATE my_badge AS t
SET t.status_info = 'SUCCESS', t.earned_at = current_timestamp
WHERE t.badge_id = 3 AND t.user_id <= 10;

CREATE EVENT BADGE_2_SILVER_EARLY_BIRD
    ON SCHEDULE EVERY 1 HOUR
        STARTS NOW()
    COMMENT '배지_2_실버_얼리버드'
    DO
UPDATE my_badge AS t
SET t.status_info = 'SUCCESS', t.earned_at = current_timestamp
WHERE t.badge_id = 4 AND 10 < t.user_id AND t.user_id <= 40;

CREATE EVENT BADGE_3_BRONZE_EARLY_BIRD
    ON SCHEDULE EVERY 1 HOUR
        STARTS NOW()
    COMMENT '배지_3_브론즈_얼리버드'
    DO
UPDATE my_badge AS t
SET t.status_info = 'SUCCESS', t.earned_at = current_timestamp
WHERE t.badge_id = 5 AND 40 < t.user_id AND t.user_id <= 100;

CREATE EVENT BADGE_4_ANSWERER_LV1
ON SCHEDULE EVERY 1 HOUR
STARTS NOW()
COMMENT '배지_4_답변자_Lv.1'
DO
UPDATE my_badge AS t
SET t.status_info = 'SUCCESS', t.earned_at = current_timestamp
WHERE t.badge_id = 6 AND 5 <= (SELECT COUNT(*)
                               FROM answer
                               WHERE answer.user_id = t.user_id);

CREATE EVENT BADGE_5_QUESTIONER_LV1
ON SCHEDULE EVERY 1 HOUR
STARTS NOW()
COMMENT '배지_5_질문자_Lv.1'
DO
UPDATE my_badge AS t
SET t.status_info = 'SUCCESS', t.earned_at = current_timestamp
WHERE t.badge_id = 7 AND 5 <= (SELECT COUNT(*)
                               FROM question
                               WHERE question.user_id = t.user_id);

CREATE EVENT BADGE_6_INFLUENCER_LV1
ON SCHEDULE EVERY 1 HOUR
STARTS NOW()
COMMENT '배지_6_인기인_Lv.1'
DO
UPDATE my_badge AS t
SET t.status_info = 'SUCCESS', t.earned_at = current_timestamp
WHERE t.badge_id = 8 AND 5 <= (SELECT COUNT(*)
                               FROM follow
                               WHERE follow.followee_id = t.user_id);

CREATE EVENT BADGE_7_CHALLENGE_MASTER_LV1
ON SCHEDULE EVERY 1 HOUR
STARTS NOW()
COMMENT '배지_7_챌린지_마스터_Lv.1'
DO
UPDATE my_badge AS t
SET t.status_info = 'SUCCESS', t.earned_at = current_timestamp
WHERE t.badge_id = 8 AND 1 <= (SELECT COUNT(*)
                               FROM my_challenge
                               WHERE my_challenge.user_id = t.user_id AND my_challenge.status_info = 'SUCCESS');

