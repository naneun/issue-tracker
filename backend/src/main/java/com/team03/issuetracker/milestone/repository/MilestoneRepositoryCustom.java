package com.team03.issuetracker.milestone.repository;

import com.team03.issuetracker.milestone.dto.MilestoneResponse;
import java.util.List;

public interface MilestoneRepositoryCustom {

	List<MilestoneResponse> findAllMilestones();
}
