package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.issue.domain.Emoji;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface EmojiRepository extends CrudRepository<Emoji, Long> {

    List<Emoji> findAll();
}
