INSERT INTO category VALUES
  (1, 'test category 1'),
  (2, 'test category 2');

INSERT INTO user(company_id, github_node_id, username, git_address, email, activated, calendar_mail, confirmation_code, introduction, created_at, points) VALUES
 (1, 'AAA', '1', 'git_add1', 'mail1', true, 'c1_mail',111,'i am first','2023-01-29',0),
 (0, 'BBB', '2', 'git_add2', 'mail2', false, 'c2_mail',222,'i am second','2023-01-30',0);

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

INSERT INTO roadmap(id, user_id, node, edge) VALUES
    (1,1,'[{id: ''1'',
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
    ]','[
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
    ]'),
    (2,2,'[{id: ''1'',
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
    ]','[
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
    ]');
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

