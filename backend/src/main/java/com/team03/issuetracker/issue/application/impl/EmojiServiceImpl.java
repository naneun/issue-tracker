package com.team03.issuetracker.issue.application.impl;

import com.team03.issuetracker.issue.repository.EmojiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmojiServiceImpl {

    private final EmojiRepository emojiRepository;
}
