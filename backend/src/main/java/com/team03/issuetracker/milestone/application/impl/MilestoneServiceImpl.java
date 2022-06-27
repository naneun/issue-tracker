package com.team03.issuetracker.milestone.application.impl;

import com.team03.issuetracker.milestone.application.MilestoneService;
import com.team03.issuetracker.milestone.domain.Milestone;
import com.team03.issuetracker.milestone.domain.dto.MilestoneCreateRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneModifyRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneResponse;
import com.team03.issuetracker.milestone.exception.MilestoneException;
import com.team03.issuetracker.milestone.repository.MilestoneRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {

	private final MilestoneRepository milestoneRepository;

	@Override
	public MilestoneResponse addMilestone(MilestoneCreateRequest createRequest) {
		Milestone savedMilestone = milestoneRepository.save(createRequest.toEntity());

		return new MilestoneResponse(savedMilestone);
	}

	@Override
	@Transactional
	public List<MilestoneResponse> findAll() {
		List<Milestone> milestones = milestoneRepository.findAll();

		return milestones.stream()
			.map(MilestoneResponse::new)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public MilestoneResponse modifyMilestone(Long id, MilestoneModifyRequest request) {
		Milestone foundMilestone = milestoneRepository.findById(id).orElseThrow(MilestoneException::new);
		return new MilestoneResponse(foundMilestone.update(request));
	}

	@Override
	@Transactional
	public List<Long> deleteById(List<Long> ids) {

		List<Milestone> milestones = ids.stream()
			.map(milestoneRepository::findById)
			.map(milestone -> milestone.orElseThrow(MilestoneException::new))
			.collect(Collectors.toList());

		milestones.stream()
			.map(Milestone::getIssues)
			.flatMap(Collection::stream)
			.collect(Collectors.toList())
			.forEach(issue -> issue.changeMilestone(null));

		milestoneRepository.deleteAllById(ids);

		return ids;
	}
}
