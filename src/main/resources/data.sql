INSERT INTO company(id, name)
VALUES (1, 'default'),
    (2, 'try fall'),
    (3, 'try catch');

INSERT INTO user(company_id, github_node_id, username, git_address, email, activated, calendar_mail,
                 confirmation_code,
                 introduction, created_at, points, image_src)
VALUES (1, 'AAA', '1', 'git_add1', 'mail1', true, 'c1_mail', 111, 'i am first', '2023-01-29', 0, 'null'),
    (2, 'BBB', '2', 'git_add2', 'mail2', false, 'c2_mail', 222, 'i am second', '2023-01-30', 0, 'null'),
    (3, 'CCC', '3', 'git_add3', 'mail3', false, 'c3_mail', 333, 'i am third', '2023-02-03', 0, 'null');

INSERT INTO badge
VALUES (1, 'super', 'test badge 1'),
    (2, 'doper', 'test badge 2');

INSERT INTO banner
VALUES (1, '2023-02-01', 'test banner 1', '2023-01-01'),
    (2, '2023-02-02', 'test banner 2', '2023-01-02');

INSERT INTO challenge
VALUES (1, 'super', '2023-02-01', 'test challenge 1', '2023-01-01'),
    (2, 'doper', '2023-02-02', 'test challenge 2', '2023-01-02');

INSERT INTO conference
VALUES (1, 'summary 1', 'title 1', 'url 1', 1),
    (2, 'summary 2', 'title 2', 'url 2', 2);

INSERT INTO feed(id, company_id, title, url, created_at, view_count, updated_at, es_id)
VALUES
    (1, '2','titie1', 'url1', '2023-02-07' , 1, '2023-02-07', 'P8x1NIYBAWExQKiWa9_K'),
    (2, '2','titie2', 'url2', '2023-02-07' , 1, '2023-02-07', 'QMx1NIYBAWExQKiWa9_K');

INSERT INTO follow
VALUES (1, 1, 2),
    (2, 2, 1),
    (3, 1, 3);

INSERT
INTO history
VALUES (1, 1, 1, 2022, 1),
    (2, 2, 2, 2022, 2);

INSERT INTO my_badge
VALUES (1, '2023-01-01 12:00:00', true, 1, 1),
    (2, '2023-01-01 12:00:00', true, 2, 2);

INSERT INTO my_challenge
VALUES (1, '2023-01-01 12:00:00', 1, false, 1, 1),
    (2, '2023-01-01 12:00:00', 1, true, 2, 2);

INSERT INTO question
(id, category_name, user_id, title, content, error_code, created_at, updated_at, chosen, view_count, likes, hidden,
 tags)
VALUES (1, 'DEV', 1, 'title 1', 'content 1', 'error code 1', current_date, current_date, 0, 0, 0, 0, ''),
    (2, 'DEV', 1, 'title 2', 'content 2', 'error code 2', current_date, current_date, 0, 0, 0, 0, '여기는,태그가,있어요');

INSERT INTO ranking
VALUES (1, 'DEV', 1),
    (2, 'DEV', 2);

INSERT INTO "read"(id,feed_id,user_id,read_at)
VALUES (1, 1, 1, current_date),
    (2, 2, 2, current_date);

INSERT INTO report
VALUES (1, 'content 1', '2023-01-01 12:00:00', 1, 1, 'feed'),
    (2, 'content 2', '2023-01-01 12:00:00', 2, 2, 'qna');

INSERT INTO roadmap(id, user_id, node, edge, tag, title, created_at, updated_at, likes)
VALUES (1, 1, '[{id: ''1'',
        type: ''input'',
        data: { label: ''An input node'' },
        position: { x: 0, y: 50 },
        sourcePosition: ''right'',
      },
      {
        id: ''2'',
        type: ''selectorNode'',
        data: { onChange: onChange, color: initBgColor },
        style: { border: ''1px solid #777'', padding: 10 },
        position: { x: 300, y: 50 },
      },
      {
        id: ''3'',
        type: ''output'',
        data: { label: ''Output A'' },
        position: { x: 650, y: 25 },
        targetPosition: ''left'',
      },
      {
        id: ''4'',
        type: ''output'',
        data: { label: ''Output B'' },
        position: { x: 650, y: 100 },
        targetPosition: ''left'',
      },
    ]', '[
      {
        id: ''e1-2'',
        source: ''1'',
        target: ''2'',
        animated: true,
        style: { stroke: ''#fff'' },
      },
      {
        id: ''e2a-3'',
        source: ''2'',
        target: ''3'',
        sourceHandle: ''a'',
        animated: true,
        style: { stroke: ''#fff'' },
      },
      {
        id: ''e2b-4'',
        source: ''2'',
        target: ''4'',
        sourceHandle: ''b'',
        animated: true,
        style: { stroke: ''#fff'' },
      },
    ]', 'tag1', 'title1', current_timestamp, current_timestamp, 0),
    (2, 2, '[{id: ''1'',
        type: ''input'',
        data: { label: ''An input node'' },
        position: { x: 0, y: 50 },
        sourcePosition: ''right'',
      },
      {
        id: ''2'',
        type: ''selectorNode'',
        data: { onChange: onChange, color: initBgColor },
        style: { border: ''1px solid #777'', padding: 10 },
        position: { x: 300, y: 50 },
      },
      {
        id: ''3'',
        type: ''output'',
        data: { label: ''Output A'' },
        position: { x: 650, y: 25 },
        targetPosition: ''left'',
      },
      {
        id: ''4'',
        type: ''output'',
        data: { label: ''Output B'' },
        position: { x: 650, y: 100 },
        targetPosition: ''left'',
      },
    ]', '[
      {
        id: ''e1-2'',
        source: ''1'',
        target: ''2'',
        animated: true,
        style: { stroke: ''#fff'' },
      },
      {
        id: ''e2a-3'',
        source: ''2'',
        target: ''3'',
        sourceHandle: ''a'',
        animated: true,
        style: { stroke: ''#fff'' },
      },
      {
        id: ''e2b-4'',
        source: ''2'',
        target: ''4'',
        sourceHandle: ''b'',
        animated: true,
        style: { stroke: ''#fff'' },
      },
    ]', 'tag2', 'title2', current_timestamp, current_timestamp, 0);
INSERT INTO subscription
VALUES (1, 1, 1),
    (2, 2, 2);

INSERT INTO today_hot
VALUES (1, 1, 'title 1'),
    (2, 2, 'title 2');

INSERT INTO withdrawal
VALUES (1, 'guess'),
    (2, 'sleep');

INSERT INTO answer
(id, question_id, user_id, content, created_at, updated_at, chosen, likes, hidden)
VALUES (1, 1, 1, '1번 답변입니다.', current_timestamp, current_timestamp, false, 0, false),
    (2, 1, 2, '2번 답변입니다.', current_timestamp, current_timestamp, false, 0, false);