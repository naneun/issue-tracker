package com.team03.issuetracker.issue.domain.dto.label;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LabelModifyRequest {

	@NotBlank
	private String title;
	private String description;
	private String backgroundColor;
}
