package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.common.config.DataJpaConfig;
import com.team03.issuetracker.issue.domain.Emoji;
import com.team03.issuetracker.issue.exception.EmojiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@Import(DataJpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class EmojiRepositoryTest {

    final EmojiRepository emojiRepository;

    final List<Emoji> registeredEmojis;

    @Autowired
    EmojiRepositoryTest(EmojiRepository emojiRepository) {
        this.emojiRepository = emojiRepository;
        this.registeredEmojis = List.of(
                Emoji.of(1L, "❤", "좋아요"),
                Emoji.of(2L, "👍", "최고에요"),
                Emoji.of(3L, "👎", "싫어요"),
                Emoji.of(4L, "✅", "확인했어요")
        );
    }

    /**
     * @implNote - 다른 사람의 코멘트에 등록할 수 있는 이모지 리스트를 출력한다.
     */
    @Test
    void 등록된_모든_이모지를_조회한다() {

        // given

        // when
        List<Emoji> emojis = emojiRepository.findAll();

        // then
        emojis.forEach((emoji) -> assertThat(emoji)
                .usingRecursiveComparison()
                .isEqualTo(registeredEmojis.get(emojis.indexOf(emoji))));
    }

    /**
     * @implNote - 출력된 이모지 리스트에서 선택한 이모지를 코멘트에 등록하기 위해 해당하는 이모지를 조회한다.
     */
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void 해당하는_ID를_가진_이모지를_조회한다(Long id) {

        // given

        // when
        Emoji foundEmoji = emojiRepository.findById(id)
                .orElseThrow(EmojiException::new);

        // then
        assertThat(foundEmoji).usingRecursiveComparison()
                .isEqualTo(registeredEmojis.get(id.intValue() - 1));
    }

    // TODO 특정 이모지를 삭제하면 해당 이모지가 등록된 댓글에서도 표기되지 않도록 한다.
}
