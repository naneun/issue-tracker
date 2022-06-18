package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(IssueService.class)
class IssueServiceTest {

    @MockBean
    IssueRepository issueRepository;

    final IssueService issueService;

    @Autowired
    IssueServiceTest(IssueService issueService) {
        this.issueService = issueService;
    }
}
