package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.domain.Emoji;
import com.team03.issuetracker.issue.repository.EmojiRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest(EmojiServiceTest.class)
class EmojiServiceTest {

    @MockBean
    EmojiRepository emojiRepository;

    final EmojiService emojiService;

    final List<Emoji> registeredEmojis;

    @Autowired
    EmojiServiceTest(EmojiService emojiService) {
        this.emojiService = emojiService;
        this.registeredEmojis = List.of(
                Emoji.of(1L, "❤", "좋아요"),
                Emoji.of(2L, "👍", "최고에요"),
                Emoji.of(3L, "👎", "싫어요"),
                Emoji.of(4L, "✅", "확인했어요")
        );
    }

    @Test
    void 등록된_모든_이모지를_조회한다() {

        // given
        given(emojiRepository.findAll()).willReturn(registeredEmojis);

        // when
        List<Emoji> emojis = null; // = emojiService.findAll();

        // then
        emojis.forEach((emoji) -> assertThat(emoji)
                .usingRecursiveComparison()
                .isEqualTo(registeredEmojis.get(emojis.indexOf(emoji))));
    }
}
