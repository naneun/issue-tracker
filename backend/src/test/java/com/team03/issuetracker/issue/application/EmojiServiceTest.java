package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.repository.EmojiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(EmojiServiceTest.class)
class EmojiServiceTest {

    @MockBean
    EmojiRepository emojiRepository;

    final EmojiService emojiService;

    @Autowired
    EmojiServiceTest(EmojiService emojiService) {
        this.emojiService = emojiService;
    }
}
