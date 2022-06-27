/* emoji */
insert into emoji(unicode, description)
values ('❤', '좋아요'),
       ('👍', '최고에요'),
       ('👎', '싫어요'),
       ('✅', '확인했어요');

/* member */
insert into member(name, email)
values ('Sam', 'yhsep7@gmail.com'),
       ('Daniel', 'gruzzimo@naver.com');

/* label */
insert into label(title, description, background_color)
values ('Label', '레이블에 대한 설명', '#b7bcc4'),
       ('Documentation', '개발 내용을 문서화', '#041c42');

/* milestone */
insert into milestone(title, description, due_date)
values ('제목', '마일스톤에 대한 설명', '2022-07-01'),
       ('마일스톤', '코드스쿼드 마일스톤', '2022-06-20');

/* issue */
insert into issue(title, content, state, label_id, milestone_id, assignee_id, creator_id)
values ('제목', '이슈에 대한 설명(최대 두 줄까지 보여줄 수 있다)', 'OPEN', 1, 1, 1, 1),
       ('안드로이드 이슈트래커', '2022년 6월 13일 월요일 부터 7월 1일 금요일 까지', 'OPEN', 2, 2, 2, 1),
       ('닫힌 이슈', '이미 닫힌 이슈입니다.', 'CLOSE', 1, 2, 1, 1);

/* comment */
insert into comment(writer_id, content, issue_id)
values (1, '마크다운이 어떻게 보여지는 지 확인합니다.', 1),
       (2, '코멘트는 어떻게 보여지는 지 확인합니다.', 1);
