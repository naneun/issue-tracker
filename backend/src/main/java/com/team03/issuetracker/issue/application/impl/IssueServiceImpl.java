package com.team03.issuetracker.issue.application.impl;

import com.team03.issuetracker.issue.application.IssueService;
import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.issue.domain.dto.issue.IssueAddRequest;
import com.team03.issuetracker.issue.domain.dto.issue.IssueModifyRequest;
import com.team03.issuetracker.issue.domain.dto.issue.IssueResponse;
import com.team03.issuetracker.issue.domain.dto.issue.IssueSearchCondition;
import com.team03.issuetracker.issue.domain.dto.issue.IssueSearchText;
import com.team03.issuetracker.issue.domain.dto.issue.IssueSimpleResponse;
import com.team03.issuetracker.issue.repository.IssueRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;

    @Override
    public List<IssueSimpleResponse> findByState(IssueState state) {
        return null;
    }

    @Override
    public IssueResponse addIssue(IssueAddRequest issueAddRequest) {
        return null;
    }

    @Override
    public IssueResponse modifyIssue(IssueModifyRequest issueModifyRequest) {
        return null;
    }

    @Override
    public List<IssueResponse> changeStateById(List<Long> checkedIds) {
        return null;
    }

    @Override
    public List<IssueResponse> deleteById(List<Long> checkedIds) {
        return null;
    }

    @Override
    public List<IssueSimpleResponse> findBySearchCondition(
        IssueSearchCondition issueSearchCondition) {
        return null;
    }

    @Override
    public List<IssueSimpleResponse> findByTextSearch(IssueSearchText issueSearchText) {
        return null;
    }
}
