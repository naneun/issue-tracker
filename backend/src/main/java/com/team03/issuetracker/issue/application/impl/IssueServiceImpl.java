package com.team03.issuetracker.issue.application.impl;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.common.repository.MemberRepository;
import com.team03.issuetracker.issue.application.IssueService;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.issue.domain.dto.issue.request.IssueAddRequest;
import com.team03.issuetracker.issue.domain.dto.issue.request.IssueModifyRequest;
import com.team03.issuetracker.issue.domain.dto.issue.request.IssueRequestDto;
import com.team03.issuetracker.issue.domain.dto.issue.request.IssueSearchCondition;
import com.team03.issuetracker.issue.domain.dto.issue.request.IssueSearchText;
import com.team03.issuetracker.issue.domain.dto.issue.response.IssueDetailResponse;
import com.team03.issuetracker.issue.domain.dto.issue.response.IssueResponse;
import com.team03.issuetracker.issue.domain.dto.issue.response.IssueSimpleResponse;
import com.team03.issuetracker.issue.exception.IssueException;
import com.team03.issuetracker.issue.repository.IssueRepository;
import com.team03.issuetracker.issue.repository.LabelRepository;
import com.team03.issuetracker.milestone.domain.Milestone;
import com.team03.issuetracker.milestone.repository.MilestoneRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

	private final IssueRepository issueRepository;

	private final LabelRepository labelRepository;
	private final MilestoneRepository milestoneRepository;
	private final MemberRepository memberRepository;

	@Override
	@Transactional(readOnly = true)
	public List<IssueSimpleResponse> findByState(IssueState state) {
		return issueRepository.findByState(state).stream()
			.map(IssueSimpleResponse::new)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public IssueDetailResponse findDetailById(Long id) {
		Issue issue = issueRepository.findById(id)
			.orElseThrow(IssueException::new);

		return IssueDetailResponse.from(issue);
	}

	@Override
	@Transactional
	public IssueResponse addIssue(IssueAddRequest issueAddRequest) {
		Issue issue = createIssue(issueAddRequest);
		issueRepository.save(issue);

		return IssueResponse.from(issue);
	}

	@Override
	@Transactional
	public IssueResponse modifyIssue(Long issueId, IssueModifyRequest issueModifyRequest) {
		Issue issue = issueRepository.findById(issueId)
			.orElseThrow(IssueException::new);

		Issue modified = createIssue(issueModifyRequest);

		return IssueResponse.from(issue.merge(modified));
	}


	/**
	 * Dto(Request) -> Entity(Issue)
	 *
	 * @param issueRequestDto
	 * @return
	 */
	private Issue createIssue(IssueRequestDto issueRequestDto) {
		Label label = labelRepository.findById(
				issueRequestDto.getLabelId() == null ? 0 : issueRequestDto.getLabelId())
			.orElse(null);

		Milestone milestone = milestoneRepository.findById(
				issueRequestDto.getMilestoneId() == null ? 0 : issueRequestDto.getMilestoneId())
			.orElse(null);

		Member assignee = memberRepository.findById(
				issueRequestDto.getAssigneeId() == null ? 0 : issueRequestDto.getMilestoneId())
			.orElse(null);

		return issueRequestDto.toEntity(label, milestone, assignee);
	}

	@Override
	@Transactional
	public List<IssueResponse> changeState(List<Long> checkedIds) {
		List<Issue> issues = issueRepository.findAllById(checkedIds);
		issues.forEach(Issue::changeState);

		return issues.stream().map(IssueResponse::from)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public List<Long> deleteById(List<Long> checkedIds) {
		List<Issue> issues = issueRepository.findAllById(checkedIds);
		issues.stream().filter(issue -> issue.getMilestone() != null)
			.forEach(issue -> issue.getMilestone().removeIssue(issue));
		issues.forEach(Issue::truncateComments);
		issueRepository.deleteAllById(checkedIds);

		return issues.stream().map(Issue::getId)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<IssueSimpleResponse> findBySearchCondition(
		IssueSearchCondition searchCondition) {

		return issueRepository.findBySearchCondition(searchCondition).stream()
			.map(IssueSimpleResponse::new)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<IssueSimpleResponse> findByTextSearch(IssueSearchText issueSearchText) {
		// TODO
		return null;
	}
}
