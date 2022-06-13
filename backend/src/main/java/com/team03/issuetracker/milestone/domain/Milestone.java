package com.team03.issuetracker.milestone.domain;

import com.team03.issuetracker.milestone.dto.MilestoneUpdateRequest;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Milestone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private LocalDate dueDate;

	public void update(MilestoneUpdateRequest request) {
		if (request.getTitle() != null) {
			this.title = request.getTitle();
		}
		if (request.getDescription() != null) {
			this.description = request.getDescription();
		}
		if (request.getDueDate() != null) {
			this.dueDate = request.getDueDate();
		}

	}

}
