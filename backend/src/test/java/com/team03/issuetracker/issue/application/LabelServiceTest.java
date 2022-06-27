package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.application.impl.LabelServiceImpl;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.issue.domain.dto.label.LabelCreateRequest;
import com.team03.issuetracker.issue.domain.dto.label.LabelModifyRequest;
import com.team03.issuetracker.issue.domain.dto.label.LabelResponse;
import com.team03.issuetracker.issue.exception.LabelException;
import com.team03.issuetracker.issue.repository.LabelRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LabelServiceTest {

	final LabelServiceImpl labelServiceImpl;
	final LabelRepository labelRepository;

	@Autowired
	public LabelServiceTest(LabelServiceImpl labelServiceImpl, LabelRepository labelRepository) {
		this.labelRepository = labelRepository;
		this.labelServiceImpl = labelServiceImpl;
	}

	@Test
	void 라벨을_생성한다() {

		// given
		LabelCreateRequest createRequest = new LabelCreateRequest("Label", "레이블에 대한 설명", "#b7bcc4");

		// when
		LabelResponse labelResponse = labelServiceImpl.addLabel(createRequest);

		// then
		Label foundLabel = labelRepository.findById(labelResponse.getId()).orElseThrow(LabelException::new);
		assertThat(labelResponse)
			.usingRecursiveComparison()
			.isEqualTo(foundLabel);
	}

	@Test
	void 라벨_목록을_조회한다() {

		// given

		// when
		List<LabelResponse> labelResponses = labelServiceImpl.findAll();

		// then
		List<Label> foundLabels = labelRepository.findAll();
		labelResponses.forEach(label -> {
			assertThat(label).usingRecursiveComparison().isEqualTo(foundLabels.get(labelResponses.indexOf(label)));
		});
	}

	@Test
	void 라벨을_편집한다() {

		// given
		Long id = 1L;
		LabelModifyRequest modifyRequest = new LabelModifyRequest("수정 제목", "수정 설명", "#ffffff");
		Label registeredLabel = labelRepository.findById(id).orElseThrow(LabelException::new);
		Label updatedLabel = Label.of(1L, "수정 제목", "수정 설명", "#ffffff", List.of());

		// when
		LabelResponse labelResponse = labelServiceImpl.modifyLabel(id, modifyRequest);

		// then
		assertThat(labelResponse)
			.usingRecursiveComparison()
			.isEqualTo(updatedLabel);
	}

	@Test
	void 라벨을_일괄_삭제한다() {

		// given
		List<Long> ids = List.of(1L, 2L);
		List<Label> foundLabels = labelRepository.findAllById(ids);

		// when
		List<Long> deletedLabelIds = labelServiceImpl.deleteById(ids);

		// then
		assertThat(foundLabels).hasSize(2);
		deletedLabelIds.forEach(id ->
			assertThat(labelRepository.findById(id)).isEmpty()
		);
	}

}
