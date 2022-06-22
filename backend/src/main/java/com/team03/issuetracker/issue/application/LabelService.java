package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.issue.domain.dto.label.LabelCreateRequest;
import com.team03.issuetracker.issue.domain.dto.label.LabelModifyRequest;
import java.util.List;

public interface LabelService {

    Label addLabel(LabelCreateRequest createRequest);

    List<Label> findAll();

    Label update(LabelModifyRequest modifyRequest);

    List<Long> deleteById(List<Long> ids);
}
