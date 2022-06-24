package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.domain.dto.label.LabelCreateRequest;
import com.team03.issuetracker.issue.domain.dto.label.LabelModifyRequest;
import com.team03.issuetracker.issue.domain.dto.label.LabelResponse;
import java.util.List;

public interface LabelService {

	LabelResponse addLabel(LabelCreateRequest createRequest);

	List<LabelResponse> findAll();

	LabelResponse update(Long id, LabelModifyRequest modifyRequest);

	List<Long> deleteById(List<Long> ids);
}
