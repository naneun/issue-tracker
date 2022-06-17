package com.team03.issuetracker.issue.application.impl;

import com.team03.issuetracker.issue.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl {

    private final CommentRepository commentRepository;
}
