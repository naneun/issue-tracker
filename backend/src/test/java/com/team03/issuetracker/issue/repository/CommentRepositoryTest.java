package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.issue.domain.Comment;
import com.team03.issuetracker.issue.domain.Emoji;
import com.team03.issuetracker.issue.exception.CommentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
class CommentRepositoryTest {

    final CommentRepository commentRepository;

    @Autowired
    CommentRepositoryTest(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Test
    void 특정_이슈에_등록된_코멘트를_페이징처리하여_조회한다() {
        // TODO 작성자, 작성시간, 내용

        // given
        Long issueId = 1L;

        // when
        Pageable pageable = PageRequest.of(0, 10);
        Page<Comment> comments = commentRepository.findByIssueId(issueId, pageable);

        // then

        comments.getContent();
    }

    @Test
    void 해당하는_ID를_가진_코멘트를_수정한다() {
        // given
        Long id = 1L;
        Comment foundComment = commentRepository.findById(id)
                .orElseThrow(CommentException::new);

        // when
        String otherContent = "otherContent";
        foundComment.changeContent(otherContent);
        commentRepository.save(foundComment);

        Comment changedComment = commentRepository.findById(foundComment.getId())
                .orElseThrow(CommentException::new);

        // then
        assertAll(
                () -> assertThat(changedComment.getContent()).isEqualTo(otherContent);
        );
    }
    
    @Test
    void 해당하는_ID를_가진_코멘트를_삭제한다() {
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
    void 해당하는_ID를_가진_코멘트에_이모지를_등록한다() {
        // given
        Emoji emoji = Emoji.of(1L, "좋아요", "❤");

        Long commentId = 1L;
        Comment foundComment = commentRepository.findById(1L)
                .orElseThrow(CommentException::new);

        // when
        foundComment.addEmoji(emoji);
        commentRepository.save(foundComment);
        Comment changedComment = commentRepository.findById(foundComment.getId())
                .orElseThrow(CommentException::new);

        // then
        assertThat(changedComment.getEmoji()).usingRecursiveComparison().isEqualTo(emoji)
    }
}
