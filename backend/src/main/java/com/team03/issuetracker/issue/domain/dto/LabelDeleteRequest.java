package com.team03.issuetracker.issue.domain.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LabelDeleteRequest {

	private List<Long> ids;
}
