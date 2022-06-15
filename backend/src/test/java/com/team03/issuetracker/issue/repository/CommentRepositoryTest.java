package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.common.config.DataJpaConfig;
import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.issue.domain.Comment;
import com.team03.issuetracker.issue.domain.Emoji;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.exception.CommentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@Import(DataJpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
class CommentRepositoryTest {

    final CommentRepository commentRepository;

    @Autowired
    CommentRepositoryTest(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * @implNote
     * - CommentResponse { 작성자, 작성시간, 내용 }
     *
     */
    @Test
    @Transactional
    void 특정_이슈에_등록된_댓글을_페이징처리하여_조회한다() {

        // given
        Long issueId = 1L;
        Issue issue = Issue.of(issueId, null, null, null, null, null);

        // when
        Pageable pageable = PageRequest.of(0, 10);
        Page<Comment> comments = commentRepository.findByIssue(issue, pageable);

        // then
        // TODO 'page 속성' 및 'content' 검증
    }

    /**
     * @implNote
     * - Comment { 작성자, 작성시간, 내용 }
     *
     */
    @Test
    @Transactional
    void 댓글을_등록한다() {

        // given
        Long memberId = 1L;
        String email = "yhsep7@gmail.com";
        Member writer = Member.of(memberId, email);
        Comment newComment = Comment.of(null, writer, "content");

        // when
        commentRepository.save(newComment);
        Comment foundComment = commentRepository.findById(newComment.getId())
                .orElseThrow(CommentException::new);

        // then
        LocalDateTime currentDateTime = LocalDateTime.now();
        assertThat(foundComment).usingRecursiveComparison().isEqualTo(newComment);
    }

    @Test
    @Transactional
    void 댓글을_수정한다() {

        // given
        Long id = 1L;
        Comment foundComment = commentRepository.findById(id)
                .orElseThrow(CommentException::new);

        // when
        String otherContent = "otherContent";
        foundComment.changeContent(otherContent);

        Comment changedComment = commentRepository.findById(foundComment.getId())
                .orElseThrow(CommentException::new);

        // then
        assertAll(
                () -> assertThat(changedComment.getContent()).isEqualTo(otherContent)
        );
    }

    @Test
    @Transactional
    void 해당하는_ID를_가진_댓글을_삭제한다() {

        // given
        Long id = 1L;
        Comment foundComment = commentRepository.findById(id)
                .orElseThrow(CommentException::new);

        // when
        commentRepository.deleteById(foundComment.getId());

        // then
        assertAll(
                () -> assertThat(foundComment).isNotNull(),
                () -> assertThat(commentRepository.findById(foundComment.getId())).isEmpty()
        );
    }

    @Test
    @Transactional
    void 현재_코멘트에_이모지를_등록한다() {

        // given
        Emoji emoji = Emoji.of(1L, null, null);

        Long commentId = 1L;
        Comment foundComment = commentRepository.findById(commentId)
                .orElseThrow(CommentException::new);

        // when
        foundComment.addEmoji(emoji);
        Comment changedComment = commentRepository.findById(foundComment.getId())
                .orElseThrow(CommentException::new);

        // then
        assertThat(changedComment.getEmoji()).usingRecursiveComparison().isEqualTo(emoji);
    }
}
