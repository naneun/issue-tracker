package com.team03.issuetracker.milestone.application.impl;

import com.team03.issuetracker.milestone.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl {

    private final MilestoneRepository milestoneRepository;
}
