package com.team03.issuetracker.milestone.repository;

import com.team03.issuetracker.milestone.domain.Milestone;
import java.util.List;

public interface MilestoneRepositoryCustom {

	List<Milestone> findAllMilestones();
}
