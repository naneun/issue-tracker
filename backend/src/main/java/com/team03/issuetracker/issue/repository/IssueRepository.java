package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.issue.domain.Comment;
import com.team03.issuetracker.issue.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {

}
