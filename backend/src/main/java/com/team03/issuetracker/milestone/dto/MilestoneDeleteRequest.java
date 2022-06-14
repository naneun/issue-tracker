package com.team03.issuetracker.milestone.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MilestoneDeleteRequest {

	private List<Long> ids;
}
