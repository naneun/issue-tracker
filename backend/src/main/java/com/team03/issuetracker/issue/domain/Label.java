package com.team03.issuetracker.issue.domain;

import com.team03.issuetracker.issue.domain.dto.LabelUpdateRequest;
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
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private String BackgroundColor;

	public void update(LabelUpdateRequest request) {
		if (request.getTitle() != null) {
			this.title = request.getTitle();
		}

		if (request.getDescription() != null) {
			this.description = request.getDescription();
		}

		if (request.getBackgroundColor() != null) {
			this.BackgroundColor = request.getBackgroundColor();
		}
	}
}
