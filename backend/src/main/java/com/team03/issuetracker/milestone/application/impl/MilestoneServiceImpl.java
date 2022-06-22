package com.team03.issuetracker.milestone.application.impl;

import com.team03.issuetracker.milestone.application.MilestoneService;
import com.team03.issuetracker.milestone.domain.Milestone;
import com.team03.issuetracker.milestone.domain.dto.MilestoneCreateRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneModifyRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneResponse;
import com.team03.issuetracker.milestone.repository.MilestoneRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;

    @Override
    public Milestone addMilestone(MilestoneCreateRequest createRequest) {
        return null;
    }

    @Override
    public List<MilestoneResponse> findAll() {
        return null;
    }

    @Override
    public Milestone update(MilestoneModifyRequest request) {
        return null;
    }
}
