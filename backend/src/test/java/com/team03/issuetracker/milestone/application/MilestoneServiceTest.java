package com.team03.issuetracker.milestone.application;

import com.team03.issuetracker.milestone.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(MilestoneService.class)
class MilestoneServiceTest {

    @MockBean
    MilestoneRepository milestoneRepository;

    final MilestoneService milestoneService;

    @Autowired
    MilestoneServiceTest(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }
}
