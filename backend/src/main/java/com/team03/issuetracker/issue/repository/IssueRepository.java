package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.issue.repository.custom.IssueRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long>, IssueRepositoryCustom {

    List<Issue> findByState(@Param("state")IssueState state);
}
