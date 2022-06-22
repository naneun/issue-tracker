package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentServiceTest {

    final CommentService commentService;

    final CommentRepository commentRepository;

    public CommentServiceTest(CommentService commentService, CommentRepository commentRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
    }

    @Test
    void 해당하는_이슈에_등록된_코멘트_리스트를_조회한다() {
        // TODO Page<CommentSimpleResponse> findByIssueId(Long issueId, Pageable pageable);
    }

    @Test
    void 해당하는_이슈에_요청_받은_코멘트를_등록한다() {
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
