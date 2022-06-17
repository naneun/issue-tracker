package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.issue.domain.dto.LabelUpdateRequest;
import com.team03.issuetracker.issue.repository.LabelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebMvcTest(LabelService.class)
class LabelServiceTest {

    final LabelService labelService;

    @MockBean
    LabelRepository labelRepository;

    @Autowired
    LabelServiceTest(LabelService labelService) {
        this.labelService = labelService;
    }

    @Test
    void 라벨을_생성한다() {

        // given
        LabelCreateRequest createRequest = new LabelCreateRequest("Label", "레이블에 대한 설명", "#b7bcc4");

        Label registeredLabel = Label.of(1L, "Label", "레이블에 대한 설명", "#b7bcc4");

        given(labelRepository.save(createRequest.toEntity())).willReturn(registeredLabel);

        // when
        Label createdLabel = labelService.add(createRequest);

        // then
        assertThat(createdLabel)
            .usingRecursiveComparison()
            .isEqualTo(registeredLabel);
    }

    // Todo : 라벨 목록 조회는 컨트롤러에서 바로 Repository.findAll() 호출?
    // Todo : 라벨 일괄 삭제는 컨트롤러에서 바로 Repository.deleteAllById() 호출?

    @Test
    void 라벨을_편집한다() {

        // given
        LabelUpdateRequest updateRequest = new LabelUpdateRequest("수정 제목", "수정 설명", "#ffffff");
        Label registeredLabel = Label.of(1L, "Label", "레이블에 대한 설명", "#b7bcc4");
        Label updatedLabel = Label.of(1L, "수정 제목", "수정 설명", "#ffffff");

        given(labelRepository.save(registeredLabel.update(updateRequest))).willReturn(updatedLabel);

        // when
        Label label = labelService.update(updateRequest);

        // then

        assertThat(label)
            .usingRecursiveComparison()
            .isEqualTo(updatedLabel);
    }
}
