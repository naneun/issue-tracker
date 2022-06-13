package com.team03.issuetracker.issue.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class LabelRepositoryTest {

    private final LabelRepository labelRepository;

    @Autowired
    private LabelRepositoryTest(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }
}