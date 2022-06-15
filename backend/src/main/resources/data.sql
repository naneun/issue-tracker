/* emoji */
insert into emoji(unicode, description)
values ('❤', '좋아요'),
       ('👍', '최고에요'),
       ('👎', '싫어요'),
       ('✅', '확인했어요');

/* member */
insert into member(email)
values ('yhsep7@gmail.com');

/* comment */
insert into comment(writer_id, content)
values (1, 'content');

/* label */
insert into label(title, description, background_color)
values ('Label', '레이블에 대한 설명', '#b7bcc4'),
       ('Documentation', '개발 내용을 문서화', '#041c42');

/* milestone */
insert into milestone(title, description, due_date)
values ('제목', '마일스톤에 대한 설명', '2022-07-01'),
       ('마일스톤', '코드스쿼드 마일스톤', '2022-06-20');
