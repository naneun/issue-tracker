package com.team03.issuetracker.issue.domain;

import com.team03.issuetracker.issue.domain.dto.label.LabelModifyRequest;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String description;

	private String backgroundColor;

	/********************************************************************/

	@Builder
	private Label(Long id, String title, String description, String backgroundColor) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.backgroundColor = backgroundColor;
	}

	public static Label of(Long id, String title, String description, String backgroundColor) {
		return Label.builder()
			.id(id)
			.title(title)
			.description(description)
			.backgroundColor(backgroundColor)
			.build();
	}

	/********************************************************************/

	public Label update(LabelModifyRequest request) {
		this.title = request.getTitle();
		this.description = request.getDescription();
		this.backgroundColor = request.getBackgroundColor();
		return this;
	}

}
