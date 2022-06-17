package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.common.config.DataJpaConfig;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.issue.domain.dto.LabelUpdateRequest;
import com.team03.issuetracker.issue.exception.LabelException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@Import(DataJpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class LabelRepositoryTest {

	final LabelRepository labelRepository;

	final EntityManager entityManager;

	final List<Label> registeredLabels;

	@Autowired
	LabelRepositoryTest(LabelRepository labelRepository, EntityManager entityManager) {
		this.labelRepository = labelRepository;
		this.entityManager = entityManager;

		registeredLabels = List.of(
			Label.of(1L, "Label", "레이블에 대한 설명", "#b7bcc4"),
			Label.of(2L, "Documentation", "개발 내용을 문서화", "#041c42")
		);
	}

	private List<Issue> getIssueByLabelId(Long labelId) {
		return entityManager.createQuery(
				"select i from Issue i where i.label.id = :labelId", Issue.class)
			.setParameter("labelId", labelId)
			.getResultList();
	}

	private Issue getIssue(Long id) {
		return entityManager.find(Issue.class, id);
	}

	@Test
	void 라벨을_생성한다() {

		// given
		Label label = Label.of(null, "제목", "설명", "#000000");

		// when
		labelRepository.save(label);

		// then
		Label foundLabel = labelRepository.findById(label.getId()).get();
		assertThat(foundLabel).usingRecursiveComparison().isEqualTo(label);
	}

	@Test
	void 라벨_목록을_조회한다() {

		// given

		// when
		List<Label> labels = labelRepository.findAll();

		// then
		labels.forEach(label -> {
				Label comparisonTarget = registeredLabels.get(
					labels.indexOf(label));
				assertThat(label)
					.usingRecursiveComparison()
					.ignoringFields("issues")
					.isEqualTo(comparisonTarget);

				//				List<Issue> issues = label.getIssues();
				//				issues.forEach(issue -> {
				//					assertThat(issue)
				//						.usingRecursiveComparison()
				//						.ignoringFields("label", "milestone", "assignee", "creator", "modifier",
				//							"comments")
				//						.isEqualTo(comparisonTarget.getIssues().get(issues.indexOf(issue)));
				//				});
			}
		);
	}

	@Test
	void 존재하지_않는_라벨을_조회_시_에러가_발생한다() {

		// given
		int size = labelRepository.findAll().size();

		// then
		assertThatThrownBy(
			() -> labelRepository.findById(size + 1L).get()).isInstanceOf(
			NoSuchElementException.class);
	}

	@Test
	void 라벨의_모든필드를_수정한다() {

		// given
		Long id = 1L;
		Label label = labelRepository.findById(id).orElseThrow(LabelException::new);
		LabelUpdateRequest request = new LabelUpdateRequest("수정된 제목1", "수정된 설명1", "#ffffff");

		// when
		label.update(request);

		// then
		Label foundLabel = labelRepository.findById(id).orElseThrow(LabelException::new);
		assertThat(foundLabel).usingRecursiveComparison().isEqualTo(label);
	}

	@Test
	void 라벨을_수정한다_일부필드() {

		// given
		Long id = 1L;
		Label label = labelRepository.findById(id).orElseThrow(LabelException::new);
		LabelUpdateRequest request = new LabelUpdateRequest(null, "수정된 설명1", null);

		// when
		label.update(request);

		// then
		Label foundLabel = labelRepository.findById(id).orElseThrow(LabelException::new);
		assertThat(foundLabel).usingRecursiveComparison().isEqualTo(label);
	}

	@Test
	void 라벨을_일괄적으로_삭제한다() {

		// given
		List<Label> foundLabels = labelRepository.findAll();
		List<Long> ids = foundLabels.stream()
			.map(Label::getId)
			.collect(Collectors.toList());

		// when
		foundLabels.forEach(label -> {
			List<Issue> issues = getIssueByLabelId(label.getId());
			issues.forEach(issue -> issue.changeLabel(null));
		});
		entityManager.flush();

		labelRepository.deleteAllByIdInBatch(ids);

		// then
		assertAll(
			() -> assertThat(foundLabels).isNotEmpty(),
			() -> assertThat(labelRepository.findAll()).isEmpty()
		);
	}
}
