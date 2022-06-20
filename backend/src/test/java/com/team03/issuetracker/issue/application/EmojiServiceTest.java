package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.domain.Emoji;
import com.team03.issuetracker.issue.domain.dto.emoji.EmojiResponse;
import com.team03.issuetracker.issue.repository.EmojiRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest(EmojiServiceTest.class)
class EmojiServiceTest {

    @MockBean
    EmojiRepository emojiRepository;

    final EntityManager entityManager;

    final EmojiService emojiService;

    final List<EmojiResponse> expectedResults;

    @Autowired
    EmojiServiceTest(EntityManager entityManager, EmojiService emojiService) {
        this.entityManager = entityManager;
        this.emojiService = emojiService;
        this.expectedResults = entityManager.createQuery("select e from Emoji e", Emoji.class)
                .getResultList()
                .stream()
                .map(EmojiResponse::new)
                .collect(Collectors.toList());
    }

    @Test
    void 코멘트에_등록_가능한_이모지_리스트를_조회한다() {

        // given
        given(emojiRepository.findAll()).willReturn(expectedResults);

        // when
        List<EmojiResponse> emojiResponses = emojiService.findAll();

        // then
        emojiResponses.forEach((response) -> assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expectedResults.get(emojiResponses.indexOf(response))));
    }
}
