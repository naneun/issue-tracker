package com.team03.issuetracker.issue.application.impl;

import com.team03.issuetracker.issue.application.EmojiService;
import com.team03.issuetracker.issue.domain.dto.emoji.EmojiResponse;
import com.team03.issuetracker.issue.repository.EmojiRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmojiServiceImpl implements EmojiService {

    private final EmojiRepository emojiRepository;

    @Override
    public List<EmojiResponse> findAll() {
        return emojiRepository.findAll().stream()
            .map(EmojiResponse::new)
            .collect(Collectors.toList());
    }
}
