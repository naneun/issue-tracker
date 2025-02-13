package com.team03.issuetracker.issue.application.impl;

import com.team03.issuetracker.issue.application.LabelService;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.issue.domain.dto.label.LabelCreateRequest;
import com.team03.issuetracker.issue.domain.dto.label.LabelModifyRequest;
import com.team03.issuetracker.issue.domain.dto.label.LabelResponse;
import com.team03.issuetracker.issue.exception.LabelException;
import com.team03.issuetracker.issue.repository.IssueRepository;
import com.team03.issuetracker.issue.repository.LabelRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {

	private final LabelRepository labelRepository;
	private final IssueRepository issueRepository;

	@Override
	public LabelResponse addLabel(LabelCreateRequest createRequest) {
		Label savedLabel = labelRepository.save(createRequest.toEntity());

		return new LabelResponse(savedLabel);
	}

	@Override
	public List<LabelResponse> findAll() {
		List<Label> foundLabels = labelRepository.findAll();

		return foundLabels.stream()
			.map(LabelResponse::new)
			.collect(Collectors.toList());
	}

	@Override
	public LabelResponse modifyLabel(Long id, LabelModifyRequest modifyRequest) {

		Label foundLabel = labelRepository.findById(id).orElseThrow(LabelException::new);
		Label modifiedLabel = foundLabel.update(modifyRequest);

		return new LabelResponse(modifiedLabel);

	}

	@Override
	public List<Long> deleteById(List<Long> labelIds) {

		List<Label> labels = labelIds.stream()
			.map(labelRepository::findById)
			.map(label -> label.orElseThrow(LabelException::new))
			.collect(Collectors.toList());

		labels.stream()
			.map(Label::getIssues)
			.flatMap(Collection::stream)
			.forEach(issue -> issue.changeLabel(null));

		labelRepository.deleteAllById(labelIds);

		return labels.stream().map(Label::getId)
			.collect(Collectors.toList());
	}
}
