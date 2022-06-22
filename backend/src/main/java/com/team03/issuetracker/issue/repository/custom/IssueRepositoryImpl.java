package com.team03.issuetracker.issue.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.dto.issue.IssueSearchCondition;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueRepositoryImpl implements IssueRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Issue> findBySearchCondition(IssueSearchCondition searchCondition) {
        return null;
    }
}
