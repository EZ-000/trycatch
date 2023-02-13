CREATE TRIGGER NEW_BADGE
AFTER INSERT ON badge FOR EACH ROW
INSERT INTO my_badge(user_id, badge_id, status_info, on_profile, earned_at)
SELECT a.id, NEW.id, 'ONGOING', true, null
FROM user a;

CREATE TRIGGER NEW_USER
AFTER INSERT ON badge FOR EACH ROW
INSERT INTO my_badge(user_id, badge_id, status_info, on_profile, earned_at)
SELECT NEW.id, b.id, 'ONGOING', true, null
FROM badge b;