package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CommentService.class)
class CommentServiceTest {

    @MockBean
    CommentRepository commentRepository;

    final EntityManager entityManager;

    final CommentService commentService;

    @Autowired
    CommentServiceTest(EntityManager entityManager, CommentService commentService) {
        this.entityManager = entityManager;
        this.commentService = commentService;
    }

    @Test
    void 해당하는_이슈에_등록된_코멘트_리스트를_조회한다() {
        // TODO Page<CommentSimpleResponse> findByIssueId(Long issueId, Pageable pageable);
    }

    @Test
    void 해당하는_이슈에_요청_받은_코멘트를_등록한다() {

        // given

        // when

        // then

        // TODO addComment(Long issueId, CommentAddRequest commentAddRequest);
    }

    @Test
    void 해당하는_이슈에_등록된_요청_받은_코멘트를_수정한다() {
        // TODO modifyComment(Long issueId, CommentModifyRequest commentModifyRequest);
    }

    @Test
    void 해당하는_이슈에_등록된_주어진_아이디에_해당하는_코멘트를_삭제한다() {
        // TODO deleteCommentById(Long issueId, Long commentId);
    }

    @Test
    void 해당하는_코멘트에_주어진_아이디에_해당하는_이모지를_등록한다() {
        // TODO addEmojiToComment(CommentAddEmojiRequest commentAddEmojiRequest);
    }
}
