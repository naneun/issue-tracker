package com.team03.issuetracker.milestone.domain;

import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.milestone.domain.dto.MilestoneUpdateRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

	@OneToMany(mappedBy = "milestone", cascade = CascadeType.PERSIST)
	private final List<Issue> issues = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String description;

	private LocalDate dueDate;

	/****************************************************************/

	@Builder
	private Milestone(Long id, String title, String description, LocalDate dueDate,
		List<Issue> issues) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.issues.addAll(issues);
	}

	public static Milestone of(Long id, String title, String description, LocalDate dueDate,
		List<Issue> issues) {
		return Milestone.builder()
			.id(id)
			.title(title)
			.description(description)
			.dueDate(dueDate)
			.issues(issues)
			.build();
	}

	public void update(MilestoneUpdateRequest request) {
		this.title = request.getTitle();
		this.description = request.getDescription();
		this.dueDate = request.getDueDate();
	}

	public void addIssue(Issue issue) {
		this.issues.add(issue);
	}

}
