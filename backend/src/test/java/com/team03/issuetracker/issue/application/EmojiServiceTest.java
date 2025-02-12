package com.team03.issuetracker.issue.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.team03.issuetracker.issue.domain.dto.emoji.EmojiResponse;
import com.team03.issuetracker.issue.repository.EmojiRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmojiServiceTest {

    final EmojiService emojiService;
    final EmojiRepository emojiRepository;

    @Autowired
    public EmojiServiceTest(EmojiService emojiService, EmojiRepository emojiRepository) {
        this.emojiService = emojiService;
        this.emojiRepository = emojiRepository;
    }

    @Test
    void 코멘트에_등록_가능한_이모지_리스트를_조회한다() {

        // given
        List<EmojiResponse> expectedResults = emojiRepository.findAll()
            .stream()
            .map(EmojiResponse::new)
            .collect(Collectors.toList());

        // when
        List<EmojiResponse> emojiResponses = emojiService.findAll();

        // then
        emojiResponses.forEach(response -> assertThat(response)
            .usingRecursiveComparison()
            .isEqualTo(expectedResults.get(emojiResponses.indexOf(response))));
    }
}
