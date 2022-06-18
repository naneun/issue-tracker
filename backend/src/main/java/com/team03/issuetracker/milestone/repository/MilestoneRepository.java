package com.team03.issuetracker.milestone.repository;

import com.team03.issuetracker.milestone.domain.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    // TODO join fetch
    List<Milestone> findAll();
}
