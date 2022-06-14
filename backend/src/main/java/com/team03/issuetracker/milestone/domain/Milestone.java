package com.team03.issuetracker.milestone.domain;

import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.milestone.dto.MilestoneUpdateRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

	@OneToMany(mappedBy = "milestone", cascade = CascadeType.PERSIST)
	private List<Issue> issues = new ArrayList<>();

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

	public void addIssue(Issue issue) {
		this.issues.add(issue);
	}

}
