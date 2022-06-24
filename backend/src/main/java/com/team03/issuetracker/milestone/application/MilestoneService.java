package com.team03.issuetracker.milestone.application;

import com.team03.issuetracker.milestone.domain.dto.MilestoneCreateRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneModifyRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneResponse;
import java.util.List;

public interface MilestoneService {

	MilestoneResponse addMilestone(MilestoneCreateRequest createRequest);

	List<MilestoneResponse> findAll();

	MilestoneResponse update(Long id, MilestoneModifyRequest request);

	List<Long> deleteById(List<Long> ids);
}
