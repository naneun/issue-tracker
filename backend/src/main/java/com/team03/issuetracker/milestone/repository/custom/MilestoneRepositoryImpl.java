package com.team03.issuetracker.milestone.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team03.issuetracker.milestone.domain.dto.MilestoneData;
import com.team03.issuetracker.milestone.domain.dto.QMilestoneData;
import java.util.List;
import lombok.RequiredArgsConstructor;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.team03.issuetracker.issue.domain.IssueState.CLOSE;
import static com.team03.issuetracker.issue.domain.IssueState.OPEN;
import static com.team03.issuetracker.issue.domain.QIssue.issue;
import static com.team03.issuetracker.milestone.domain.QMilestone.milestone;

@RequiredArgsConstructor
public class MilestoneRepositoryImpl implements MilestoneRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<MilestoneData> findAllMilestones() {
		return queryFactory.select(new QMilestoneData(
				milestone.title,
				milestone.description,
				milestone.dueDate,
				select(issue.count()).from(issue).where(issue.state.eq(OPEN)),
				select(issue.count()).from(issue).where(issue.state.eq(CLOSE))))
			.from(milestone)
			.leftJoin(milestone.issues, issue)
			.fetch();

		//		private BooleanExpression
		// Todo : 서브쿼리 안쓰고 하려면 어떻게 해야할까?
	}

}
