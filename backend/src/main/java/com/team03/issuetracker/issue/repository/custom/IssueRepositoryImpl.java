package com.team03.issuetracker.issue.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.dto.issue.IssueSearchCondition;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class IssueRepositoryImpl implements IssueRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Issue> findBySearchCondition(IssueSearchCondition searchCondition) {
        return null;
    }
}
