package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(LabelService.class)
class LabelServiceTest {

    @MockBean
    LabelRepository labelRepository;

    final LabelService labelService;

    @Autowired
    LabelServiceTest(LabelService labelService) {
        this.labelService = labelService;
    }
}
