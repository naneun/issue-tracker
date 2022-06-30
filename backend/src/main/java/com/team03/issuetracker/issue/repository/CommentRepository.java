package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.issue.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query(value = "select c from Comment c "
		+ "left join fetch c.writer "
		+ "left join fetch c.emoji "
		+ "where c.issue.id = :issueId"
		, countQuery = "select count(c) from Comment c where c.issue.id = :issueId")
	Page<Comment> findByIssueId(@Param("issueId") Long issueId, Pageable pageable);
}
