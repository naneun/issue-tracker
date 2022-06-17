package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.issue.domain.Emoji;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmojiRepository extends CrudRepository<Emoji, Long> {

    List<Emoji> findAll();
}
