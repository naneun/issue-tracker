package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.issue.domain.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {

}
