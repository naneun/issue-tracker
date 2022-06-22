package com.team03.issuetracker.issue.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.issue.domain.dto.label.LabelCreateRequest;
import com.team03.issuetracker.issue.domain.dto.label.LabelModifyRequest;
import com.team03.issuetracker.issue.repository.LabelRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(LabelService.class)
class LabelServiceTest {

    final LabelService labelService;
    final List<Label> registeredLabels;

    @MockBean
    LabelRepository labelRepository;

    @Autowired
    LabelServiceTest(LabelService labelService) {
        this.labelService = labelService;
        this.registeredLabels = List.of(
            Label.of(1L, "Label", "레이블에 대한 설명", "#b7bcc4"),
            Label.of(2L, "Documentation", "개발 내용을 문서화", "#041c42")
        );
    }

    @Test
    void 라벨을_생성한다() {

        // given
        LabelCreateRequest createRequest = new LabelCreateRequest("Label", "레이블에 대한 설명", "#b7bcc4");

        Label registeredLabel = registeredLabels.get(0);

        given(labelRepository.save(createRequest.toEntity())).willReturn(registeredLabel);

        // when
        Label createdLabel = labelService.addLabel(createRequest);

        // then
        assertThat(createdLabel)
            .usingRecursiveComparison()
            .isEqualTo(registeredLabel);
    }

    @Test
    void 라벨_목록을_조회한다() {

        // given
        given(labelRepository.findAll()).willReturn(registeredLabels);

        // when
        List<Label> labels = labelService.findAll();

        // then
        labels.forEach(label -> {
            assertThat(label).usingRecursiveComparison()
                .isEqualTo(registeredLabels.get(labels.indexOf(label)));
        });
    }

    @Test
    void 라벨을_편집한다() {

        // given
        LabelModifyRequest modifyRequest = new LabelModifyRequest("수정 제목", "수정 설명", "#ffffff");
        Label registeredLabel = registeredLabels.get(0);
        Label updatedLabel = Label.of(1L, "수정 제목", "수정 설명", "#ffffff");

        given(labelRepository.save(registeredLabel.update(modifyRequest))).willReturn(updatedLabel);

        // when
        Label label = labelService.update(modifyRequest);

        // then
        assertThat(label)
            .usingRecursiveComparison()
            .isEqualTo(updatedLabel);
    }

    // TODO
    @Test
    void 라벨을_일괄_삭제한다() {

/*		// given
		List<Long> ids = List.of(1L, 2L);
		given(labelRepository.deleteAllByIdInBatch(ids)).willReturn(ids);

		// when
		List<Long> deletedLabelIds = labelService.deleteById(ids);

		// then
		assertThat(deletedLabelIds).isEqualTo(ids);*/
    }
}
