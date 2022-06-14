package com.team03.issuetracker.milestone.repository;

import com.team03.issuetracker.milestone.dto.MilestoneData;
import java.util.List;

public interface MilestoneRepositoryCustom {

	List<MilestoneData> findAllMilestones();
}
