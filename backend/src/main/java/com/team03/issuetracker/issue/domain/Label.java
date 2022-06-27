package com.team03.issuetracker.issue.domain;

import com.team03.issuetracker.issue.domain.dto.label.LabelModifyRequest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Label {

	@OneToMany(mappedBy = "label", fetch = FetchType.LAZY)
	@ToString.Exclude
	private final List<Issue> issues = new ArrayList<>();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private String backgroundColor;

	/********************************************************************/

	@Builder
	private Label(Long id, String title, String description, String backgroundColor,
		List<Issue> issues) {

		this.id = id;
		this.title = title;
		this.description = description;
		this.backgroundColor = backgroundColor;
		if (issues == null) {
			issues = new ArrayList<>();
		}
		this.issues.addAll(issues);
	}

	public static Label of(Long id, String title, String description, String backgroundColor,
		List<Issue> issues) {

		return Label.builder()
			.id(id)
			.title(title)
			.description(description)
			.backgroundColor(backgroundColor)
			.issues(issues)
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
