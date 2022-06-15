package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.issue.domain.Comment;
import com.team03.issuetracker.issue.domain.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByIssue(@Param("issue") Issue issue, Pageable pageable);
}
