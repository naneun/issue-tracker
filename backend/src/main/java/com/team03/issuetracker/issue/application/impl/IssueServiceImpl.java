package com.team03.issuetracker.issue.application.impl;

import com.team03.issuetracker.issue.application.IssueService;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.issue.domain.dto.issue.IssueAddRequest;
import com.team03.issuetracker.issue.domain.dto.issue.IssueModifyRequest;
import com.team03.issuetracker.issue.domain.dto.issue.IssueResponse;
import com.team03.issuetracker.issue.domain.dto.issue.IssueSearchCondition;
import com.team03.issuetracker.issue.domain.dto.issue.IssueSearchText;
import com.team03.issuetracker.issue.domain.dto.issue.IssueSimpleResponse;
import com.team03.issuetracker.issue.exception.IssueException;
import com.team03.issuetracker.issue.repository.IssueRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

	private final IssueRepository issueRepository;

	@Qualifier("addRequestToIssueMapper")
	private final ModelMapper requestMapper;

	@Qualifier("modifiedRequestToIssueMapper")
	private final ModelMapper modifiedMapper;

	@Override
	@Transactional(readOnly = true)
	public List<IssueSimpleResponse> findByState(IssueState state) {
		return issueRepository.findByState(state).stream()
			.map(IssueSimpleResponse::new)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public IssueResponse addIssue(IssueAddRequest issueAddRequest) {
		Issue issue = requestMapper.map(issueAddRequest, Issue.class);
		issueRepository.save(issue);

		return IssueResponse.from(issue);
	}

	@Override
	@Transactional
	public IssueResponse modifyIssue(IssueModifyRequest issueModifyRequest) {
		Issue issue = issueRepository.findById(issueModifyRequest.getId())
			.orElseThrow(IssueException::new);

		Issue modified = modifiedMapper.map(issueModifyRequest, Issue.class);

		return IssueResponse.from(issue.merge(modified));
	}

	@Override
	@Transactional
	public List<IssueResponse> changeStateById(List<Long> checkedIds) {
		List<Issue> issues = issueRepository.findAllById(checkedIds);
		issues.forEach(Issue::changeState);

		return issues.stream().map(IssueResponse::from)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public List<Long> deleteById(List<Long> checkedIds) {
		// TODO
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<IssueSimpleResponse> findBySearchCondition(
		IssueSearchCondition issueSearchCondition) {
		// TODO
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<IssueSimpleResponse> findByTextSearch(IssueSearchText issueSearchText) {
		// TODO
		return null;
	}
}
