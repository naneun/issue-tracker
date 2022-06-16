package com.team03.issuetracker.milestone.repository;

import com.team03.issuetracker.common.config.DataJpaConfig;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.milestone.domain.Milestone;
import com.team03.issuetracker.milestone.domain.dto.MilestoneUpdateRequest;
import com.team03.issuetracker.milestone.exception.MilestoneException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@Import(DataJpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MilestoneRepositoryTest {

	final List<Milestone> registeredMilestones;

	final MilestoneRepository milestoneRepository;

	final EntityManager entityManager;

	@Autowired
	public MilestoneRepositoryTest(
		MilestoneRepository milestoneRepository, EntityManager entityManager) {
		this.milestoneRepository = milestoneRepository;
		this.entityManager = entityManager;

		List<Issue> milestone1Issues = entityManager.createQuery(
			"select i from Issue i where i.milestone.id = 1L", Issue.class).getResultList();
		List<Issue> milestone2Issues = entityManager.createQuery(
			"select i from Issue i where i.milestone.id = 2L", Issue.class).getResultList();

		registeredMilestones = List.of(
			Milestone.of(1L, "제목", "마일스톤에 대한 설명", LocalDate.of(2022, 7, 1),
				milestone2Issues),
			Milestone.of(2L, "마일스톤", "코드스쿼드 마일스톤", LocalDate.of(2022, 6, 20),
				milestone2Issues)
		);
	}

	@Test
	void 마일스톤을_생성한다() {
		// given
		Milestone milestone = Milestone.builder()
			.title("제목")
			.description("설명")
			.dueDate(LocalDate.of(2022, 6, 30))
			.issues(new ArrayList<>())
			.build();

		// when
		Long id = milestoneRepository.save(milestone).getId();

		// then
		Milestone foundMilestone = milestoneRepository.findById(id)
			.orElseThrow(MilestoneException::new);
		assertThat(foundMilestone).usingRecursiveComparison().isEqualTo(milestone);

	}

	@Test
	void 마일스톤_목록을_조회한다() {
		// given

		// when
		List<Milestone> foundMilestones = milestoneRepository.findAll();

		// then
		foundMilestones.stream().forEach(milestone ->
		{
			assertThat(milestone)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes("issues.*")
				.isEqualTo(registeredMilestones.get(foundMilestones.indexOf(milestone)));
		});
	}

	@Test
	void 마일스톤을_수정한다_모든필드() {
		// given
		Milestone milestone = milestoneRepository.findById(1L).orElseThrow(MilestoneException::new);
		MilestoneUpdateRequest request = new MilestoneUpdateRequest("수정된 제목1", "수정된 설명1",
			LocalDate.of(2022, 7, 25));

		// when
		milestone.update(request);

		// then
		Milestone foundMilestone = milestoneRepository.findById(milestone.getId())
			.orElseThrow(MilestoneException::new);
		assertThat(foundMilestone).usingRecursiveComparison()
			.ignoringFields("issues")
			.isEqualTo(milestone);
	}

	@Test
	void 마일스톤을_수정한다_일부필드() {
		// given
		Milestone milestone = milestoneRepository.findById(1L)
			.orElseThrow(MilestoneException::new);
		MilestoneUpdateRequest request = new MilestoneUpdateRequest(null, "수정된 설명1", null);

		// when
		milestone.update(request);

		// then
		Milestone foundMilestone = milestoneRepository.findById(milestone.getId())
			.orElseThrow(MilestoneException::new);
		assertThat(foundMilestone).usingRecursiveComparison().isEqualTo(milestone);

	}

	@Test
	void 마일스톤을_일괄적으로_삭제한다() {
		// given

		List<Long> milestoneIds = List.of(1L, 2L);

		List<Issue> resultList = entityManager.createQuery(
				"select i from Issue i where i.milestone.id in :list", Issue.class)
			.setParameter("list", milestoneIds)
			.getResultList();

		// when
		resultList.forEach(i -> i.changeMilestone(null));
		entityManager.flush();
		milestoneRepository.deleteAllById(milestoneIds);

		// then
		List<Milestone> milestones = milestoneRepository.findAll();

		assertThat(milestones).hasSize(0);
		assertThat(milestoneRepository.findById(1L)).isEmpty();
		assertThat(milestoneRepository.findById(2L)).isEmpty();
	}

}
