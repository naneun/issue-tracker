package com.team03.issuetracker.milestone.application;

import com.team03.issuetracker.milestone.domain.Milestone;
import com.team03.issuetracker.milestone.domain.dto.MilestoneCreateRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneModifyRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneResponse;
import java.util.List;

public interface MilestoneService {

	Milestone addMilestone(MilestoneCreateRequest createRequest);

	List<MilestoneResponse> findAll();

	Milestone update(MilestoneModifyRequest request);
}
