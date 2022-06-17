package com.team03.issuetracker.issue.application.impl;

import com.team03.issuetracker.issue.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LabelServiceImpl {

    private final LabelRepository labelRepository;
}
