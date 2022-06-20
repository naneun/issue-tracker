package com.team03.issuetracker.milestone.domain.dto;

import com.team03.issuetracker.milestone.domain.Milestone;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
public class MilestoneCreateRequest {

	@NotBlank
	private String title;

	private String description;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dueDate;

	public Milestone toEntity() {
		return Milestone.builder()
			.title(title)
			.description(description)
			.dueDate(dueDate)
			.build();
	}
}
