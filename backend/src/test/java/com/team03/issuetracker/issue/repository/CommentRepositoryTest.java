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

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@Import(DataJpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CommentRepositoryTest {

    final EntityManager entityManager;

    final CommentRepository commentRepository;

    @Autowired
    CommentRepositoryTest(EntityManager entityManager, CommentRepository commentRepository) {
        this.entityManager = entityManager;
        this.commentRepository = commentRepository;
    }

    /**
     * @implNote
     * - CommentResponse { 작성자, 작성시간, 내용 }
     *
     */
    @Test
    void 특정_이슈에_등록된_댓글을_페이징처리하여_조회한다() {

        // given
        Long issueId = 1L;
        Issue issue = entityManager.find(Issue.class, issueId);

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
    void 댓글을_등록한다() {

        // given
        Long writerId = 1L;
        Member writer = entityManager.find(Member.class, writerId);
        Comment newComment = Comment.of(null, writer, "content");
        commentRepository.save(newComment);

        // when
        Comment foundComment = commentRepository.findById(newComment.getId())
                .orElseThrow(CommentException::new);

        // then
        assertThat(foundComment).usingRecursiveComparison().isEqualTo(newComment);
    }

    @Test
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
    void 현재_코멘트에_이모지를_등록한다() {

        // given
        Emoji emoji = entityManager.find(Emoji.class, 1L);

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
