package com.team03.issuetracker.milestone.repository;

import com.team03.issuetracker.issue.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Comment, Long> {

}
