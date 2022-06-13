package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.issue.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Comment, Long> {

}
