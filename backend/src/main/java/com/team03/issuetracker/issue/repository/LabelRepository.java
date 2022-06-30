package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.issue.domain.Label;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {

	@EntityGraph(attributePaths = "issues")
	Optional<Label> findById(Long id);
}
