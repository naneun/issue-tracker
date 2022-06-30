package com.team03.issuetracker.issue.repository.custom;

import static com.team03.issuetracker.issue.domain.QIssue.issue;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.issue.domain.dto.issue.request.IssueSearchCondition;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueRepositoryImpl implements IssueRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Issue> findBySearchCondition(IssueSearchCondition searchCondition) {

		return jpaQueryFactory
			.selectFrom(issue).distinct()
			.leftJoin(issue.label).fetchJoin()
			.leftJoin(issue.milestone).fetchJoin()
			.leftJoin(issue.creator).fetchJoin()
			.where(
				stateEqualsTo(searchCondition.getState()),
				creatorEqualsTo(searchCondition.getCreatorId()),
				labelEqualsTo(searchCondition.getLabelId()),
				milestoneEqualsTo(searchCondition.getMilestoneId())
			)
			.fetch();
	}

	private BooleanExpression stateEqualsTo(IssueState state) {
		return state != null ? issue.state.eq(state) : null;
	}

	private BooleanExpression creatorEqualsTo(Long creatorId) {
		return creatorId != null ? issue.creator.id.eq(creatorId) : null;
	}

	private BooleanExpression labelEqualsTo(Long labelId) {
		return labelId != null ? issue.label.id.eq(labelId) : null;
	}

	private BooleanExpression milestoneEqualsTo(Long milestoneId) {
		return milestoneId != null ? issue.milestone.id.eq(milestoneId) : null;
	}
}
