package com.team03.issuetracker.milestone.repository;

import com.team03.issuetracker.common.config.DataJpaConfig;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.milestone.domain.Milestone;
import com.team03.issuetracker.milestone.domain.dto.MilestoneUpdateRequest;
import com.team03.issuetracker.milestone.exception.MilestoneException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@Import(DataJpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MilestoneRepositoryTest {

	final EntityManager entityManager;

	final MilestoneRepository milestoneRepository;

	final List<Milestone> registeredMilestones;

	@Autowired
	public MilestoneRepositoryTest(MilestoneRepository milestoneRepository,
		EntityManager entityManager) {
		this.milestoneRepository = milestoneRepository;
		this.entityManager = entityManager;

		registeredMilestones = List.of(
			Milestone.of(1L, "제목", "마일스톤에 대한 설명",
				LocalDate.of(2022, 7, 1), getIssuesByMilestoneId(1L)),

			Milestone.of(2L, "마일스톤", "코드스쿼드 마일스톤",
				LocalDate.of(2022, 6, 20), getIssuesByMilestoneId(2L))
		);
	}

	private List<Issue> getIssuesByMilestoneId(Long milestoneId) {
		return entityManager.createQuery(
				"select i from Issue i where i.milestone.id = :milestoneId", Issue.class)
			.setParameter("milestoneId", milestoneId)
			.getResultList();
	}

	private Issue getIssue(Long id) {
		return entityManager.find(Issue.class, id);
	}

	@Test
	void 마일스톤을_생성한다() {

		// given
		Milestone milestone = Milestone.of(null, "제목", "설명",
			LocalDate.of(2022, 6, 30), List.of(getIssue(1L), getIssue(2L)));

		// when
		milestoneRepository.save(milestone);

		// then
		Milestone foundMilestone = milestoneRepository.findById(milestone.getId())
			.orElseThrow(MilestoneException::new);

		assertThat(foundMilestone).usingRecursiveComparison().isEqualTo(milestone);
	}

	@Test
	void 마일스톤_목록을_조회한다() {

		// given

		// when
		List<Milestone> foundMilestones = milestoneRepository.findAll();

		// then
		foundMilestones.forEach(milestone -> {
				Milestone comparisonTarget = registeredMilestones.get(
					foundMilestones.indexOf(milestone));
				assertThat(milestone)
					.usingRecursiveComparison()
					.ignoringFields("issues")
					.isEqualTo(comparisonTarget);

				List<Issue> issues = milestone.getIssues();
				issues.forEach(issue -> {
					assertThat(issue)
						.usingRecursiveComparison()
						.ignoringFields("label", "milestone", "assignee", "creator", "modifier",
							"comments")
						.isEqualTo(comparisonTarget.getIssues().get(issues.indexOf(issue)));
				});
			}
		);
	}

	@Test
	void 마일스톤_내의_모든_필드를_수정한다() {

		// given
		Milestone milestone = milestoneRepository.findById(1L)
			.orElseThrow(MilestoneException::new);

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
	void 마일스톤_내의_일부_필드를_수정한다() {

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
		List<Milestone> foundMilestones = milestoneRepository.findAll();
		List<Long> milestoneIds = foundMilestones.stream()
			.map(Milestone::getId)
			.collect(Collectors.toList());

		// when
		foundMilestones.forEach(Milestone::truncateIssues);
		entityManager.flush();

		milestoneRepository.deleteAllByIdInBatch(milestoneIds);

		// then
		assertAll(
			() -> assertThat(foundMilestones).isNotEmpty(),
			() -> assertThat(milestoneRepository.findAll()).isEmpty()
		);
	}
}
