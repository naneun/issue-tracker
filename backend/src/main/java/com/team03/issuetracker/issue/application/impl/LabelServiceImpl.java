package com.team03.issuetracker.issue.application.impl;

import com.team03.issuetracker.issue.application.LabelService;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.issue.domain.dto.label.LabelCreateRequest;
import com.team03.issuetracker.issue.domain.dto.label.LabelModifyRequest;
import com.team03.issuetracker.issue.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;

    @Override
    public Label addLabel(LabelCreateRequest createRequest) {
        return null;
    }

    @Override
    public List<Label> findAll() {
        return null;
    }

    @Override
    public Label update(LabelModifyRequest modifyRequest) {
        return null;
    }

    @Override
    public List<Long> deleteById(List<Long> ids) {
        return null;
    }
}
