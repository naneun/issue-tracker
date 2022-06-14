package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.issue.domain.Emoji;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmojiRepository extends JpaRepository<Emoji, Long> {

}
