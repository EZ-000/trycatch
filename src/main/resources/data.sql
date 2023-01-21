INSERT INTO category VALUES
  (1, 'test category 1'),
  (2, 'test category 2');

INSERT INTO user VALUES
 (1, true, 'mail', 1234, false, '2023-01-01', 'email', 'address 1', 'node id 1', 0, 'user 1'),
 (2, true, 'mail', 1234, false, '2023-01-02', 'email', 'address 2', 'node id 2', 0, 'user 2');

INSERT INTO badge VALUES
                      (1, 'super', 'test badge 1'),
                      (2, 'doper', 'test badge 2');

INSERT INTO banner VALUES
                       (1, '2023-02-01', 'test banner 1', '2023-01-01'),
                       (2, '2023-02-02', 'test banner 2', '2023-01-02');

INSERT INTO challenge VALUES
                          (1, 'super', '2023-02-01', 'test challenge 1', '2023-01-01'),
                          (2, 'doper', '2023-02-02', 'test challenge 2', '2023-01-02');

INSERT INTO company VALUES
                        (1, 'try catch'),
                        (2, 'try fall');

INSERT INTO conference VALUES
                           (1, 'summary 1', 'title 1', 'url 1', 1),
                           (2, 'summary 2', 'title 2', 'url 2', 2);

INSERT INTO feed VALUES
                     (1, '2023-01-01', 'title 1', '2023-01-01 12:00:00', 'url 1', 0, 1),
                     (2, '2023-01-02', 'title 2', '2023-01-01 12:00:00', 'url 2', 0, 2);

INSERT INTO follow VALUES
                       (1, 1, 2),
                       (2, 2, 1);

INSERT INTO history VALUES
                        (1, 1, 1, 2022, 1),
                        (2, 2, 2, 2022, 2);

INSERT INTO my_badge VALUES
                         (1, '2023-01-01 12:00:00', true, 1, 1),
                         (2, '2023-01-01 12:00:00', true, 2, 2);

INSERT INTO my_challenge VALUES
                             (1, '2023-01-01 12:00:00', 1, false, 1, 1),
                             (2, '2023-01-01 12:00:00', 1, true, 2, 2);

INSERT INTO question VALUES
 (1, false, 'content 1', '2023-01-01', 'error code 1', false, 0, 'title 1', '2023-01-01 12:00:00', 0, 1, 1),
 (2, true, 'content 2', '2023-01-02', 'error code 2', false, 0, 'title 2', '2023-01-01 12:00:00', 0, 2, 2);

INSERT INTO ranking VALUES
                        (1, 1, 1),
                        (2, 2, 2);

INSERT INTO "read" VALUES
                       (1, false, 1, 1),
                       (2, true, 2, 2);

INSERT INTO report VALUES
                       (1, 'content 1', '2023-01-01 12:00:00', 1, 1, 'feed'),
                       (2, 'content 2', '2023-01-01 12:00:00', 2, 2, 'qna');

INSERT INTO roadmap VALUES
                        (1, 'roadmap 1'),
                        (2, 'roadmap 2');

INSERT INTO subscription VALUES
                             (1, 1, 1),
                             (2, 2, 2);

INSERT INTO today_hot VALUES
                          (1, 1, 'title 1'),
                          (2, 2, 'title 2');

INSERT INTO withdrawal VALUES
                           (1, 'guess'),
                           (2, 'sleep');

INSERT INTO answer VALUES
 (1, false, 'answer content', '2023-01-01', false, 1, 1, 1),
 (2, false, 'answer content', '2023-01-01', false, 2, 2, 2);

