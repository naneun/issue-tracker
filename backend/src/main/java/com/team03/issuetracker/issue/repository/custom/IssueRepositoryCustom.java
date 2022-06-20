package com.team03.issuetracker.issue.repository.custom;

import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.dto.issue.IssueSearchCondition;

import java.util.List;

public interface IssueRepositoryCustom {

    List<Issue> findBySearchCondition(IssueSearchCondition searchCondition);
}
