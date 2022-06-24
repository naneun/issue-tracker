package com.team03.issuetracker.common.config;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.common.exception.MemberException;
import com.team03.issuetracker.common.repository.MemberRepository;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.issue.domain.dto.issue.IssueAddRequest;
import com.team03.issuetracker.issue.domain.dto.issue.IssueModifyRequest;
import com.team03.issuetracker.issue.exception.LabelException;
import com.team03.issuetracker.issue.repository.LabelRepository;
import com.team03.issuetracker.milestone.domain.Milestone;
import com.team03.issuetracker.milestone.exception.MilestoneException;
import com.team03.issuetracker.milestone.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DomainMapperConfig {

	private final LabelRepository labelRepository;
	private final MilestoneRepository milestoneRepository;
	private final MemberRepository memberRepository;

	@Bean
	@Qualifier("addRequestToIssueMapper")
	public ModelMapper addRequestToIssueMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper
			.getConfiguration()
			.setSkipNullEnabled(true);

		modelMapper
			.typeMap(IssueAddRequest.class, Issue.class)
			.addMappings(
				mapper -> {
					mapper.map(IssueAddRequest::getTitle, Issue::changeTitle);
					mapper.map(IssueAddRequest::getContent, Issue::changeContent);
					mapper.map(IssueAddRequest::getState, Issue::setState);
					mapper.using((Converter<Long, Label>) converter ->
							labelRepository.findById(converter.getSource())
								.orElseThrow(LabelException::new)
						)
						.map(IssueAddRequest::getLabelId, Issue::changeLabel);

					mapper.using((Converter<Long, Milestone>) converter ->
							milestoneRepository.findById(converter.getSource())
								.orElseThrow(MilestoneException::new)
						)
						.map(IssueAddRequest::getMilestoneId, Issue::changeMilestone);

					mapper.using((Converter<Long, Member>) converter ->
							memberRepository.findById(converter.getSource())
								.orElseThrow(MemberException::new)
						)
						.map(IssueAddRequest::getAssigneeId, Issue::changeAssignee);
				}
			);

		return modelMapper;
	}

	@Bean
	@Qualifier("modifiedRequestToIssueMapper")
	public ModelMapper modifiedRequestToIssueMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper
			.getConfiguration()
			.setSkipNullEnabled(true);

		modelMapper
			.typeMap(IssueModifyRequest.class, Issue.class)
			.addMappings(
				mapper -> {
					mapper.map(IssueModifyRequest::getTitle, Issue::changeTitle);
					mapper.map(IssueModifyRequest::getContent, Issue::changeContent);
					mapper.using((Converter<Long, Label>) converter ->
							labelRepository.findById(converter.getSource())
								.orElseThrow(LabelException::new)
						)
						.map(IssueModifyRequest::getLabelId, Issue::changeLabel);

					mapper.using((Converter<Long, Milestone>) converter ->
							milestoneRepository.findById(converter.getSource())
								.orElseThrow(MilestoneException::new)
						)
						.map(IssueModifyRequest::getMilestoneId, Issue::changeMilestone);

					mapper.using((Converter<Long, Member>) converter ->
							memberRepository.findById(converter.getSource())
								.orElseThrow(MemberException::new)
						)
						.map(IssueModifyRequest::getAssigneeId, Issue::changeAssignee);
				}
			);

		return modelMapper;
	}
}
