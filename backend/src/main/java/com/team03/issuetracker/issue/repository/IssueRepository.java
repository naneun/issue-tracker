package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.issue.repository.custom.IssueRepositoryCustom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface IssueRepository extends JpaRepository<Issue, Long>, IssueRepositoryCustom {

	@EntityGraph(attributePaths = {"label", "milestone", "assignee", "creator", "modifier",
		"comments"})
	Optional<Issue> findById(@Param("id") Long id);

	@EntityGraph(attributePaths = {"label", "milestone", "assignee", "creator", "modifier",
		"comments"})
	List<Issue> findByState(@Param("state") IssueState state);

	List<Issue> findAllByLabelId(Long id);
}
