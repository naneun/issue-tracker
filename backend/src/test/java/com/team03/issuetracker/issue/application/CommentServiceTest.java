package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CommentService.class)
class CommentServiceTest {

    @MockBean
    CommentRepository commentRepository;

    final CommentService commentService;

    @Autowired
    CommentServiceTest(CommentService commentService) {
        this.commentService = commentService;
    }
}
