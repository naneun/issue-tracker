package com.team03.issuetracker.milestone.domain;

import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.milestone.domain.dto.MilestoneUpdateRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Milestone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String title;

	private String description;

	private LocalDate dueDate;

	@OneToMany(mappedBy = "milestone") //, cascade = CascadeType.PERSIST)
	@ToString.Exclude
	private final List<Issue> issues = new ArrayList<>();

	/********************************************************************/

	@Builder
	private Milestone(Long id, String title, String description, LocalDate dueDate, List<Issue> issues) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.issues.addAll(issues);
	}

	public static Milestone of(Long id, String title, String description, LocalDate dueDate, List<Issue> issues) {
		return Milestone.builder()
			.id(id)
			.title(title)
			.description(description)
			.dueDate(dueDate)
			.issues(issues)
			.build();
	}

	/********************************************************************/

	public void update(MilestoneUpdateRequest request) {
		this.title = request.getTitle();
		this.description = request.getDescription();
		this.dueDate = request.getDueDate();
	}

	public void addIssue(Issue issue) {
		this.issues.add(issue);
	}

	public void truncateIssues() {
		this.issues.forEach(issue -> issue.changeMilestone(null));
		this.issues.clear();
	}
}
