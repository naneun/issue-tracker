package com.team03.issuetracker.milestone.repository;

import com.team03.issuetracker.milestone.domain.Milestone;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

	@EntityGraph(attributePaths = "issues")
	List<Milestone> findAll();

}
