package com.team03.issuetracker.milestone.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team03.issuetracker.milestone.domain.Milestone;
import java.util.List;
import javax.persistence.EntityManager;

public class MilestoneRepositoryImpl implements MilestoneRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public MilestoneRepositoryImpl(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public List<Milestone> findAllMilestones() {
		return null;
	}
}
