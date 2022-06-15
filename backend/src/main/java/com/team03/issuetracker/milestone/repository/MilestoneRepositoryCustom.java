package com.team03.issuetracker.milestone.repository;

import com.team03.issuetracker.milestone.domain.dto.MilestoneData;
import java.util.List;

public interface MilestoneRepositoryCustom {

	List<MilestoneData> findAllMilestones();
}
