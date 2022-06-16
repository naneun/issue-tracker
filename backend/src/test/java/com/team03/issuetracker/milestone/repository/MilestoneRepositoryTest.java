package com.team03.issuetracker.milestone.repository;

import com.team03.issuetracker.common.config.DataJpaConfig;
import com.team03.issuetracker.milestone.domain.Milestone;
import com.team03.issuetracker.milestone.domain.dto.MilestoneUpdateRequest;
import com.team03.issuetracker.milestone.exception.MilestoneException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

	MilestoneRepository milestoneRepository;
	@PersistenceContext
	EntityManager em;

	@Autowired
	public MilestoneRepositoryTest(MilestoneRepository milestoneRepository) {
		this.milestoneRepository = milestoneRepository;
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
		Milestone foundMilestone = milestoneRepository.findById(id).get();
		assertThat(foundMilestone).usingRecursiveComparison().isEqualTo(milestone);

	}

	@Test
	void 마일스톤_목록을_조회한다() {
		// given
		Milestone milestone1 = Milestone.of(null, "제목1", "설명1", LocalDate.of(2022, 7, 10),
			new ArrayList<>());
		Milestone milestone2 = Milestone.of(null, "제목2", "설명2", LocalDate.of(2022, 8, 20),
			new ArrayList<>());
		Milestone milestone3 = Milestone.of(null, "제목3", "설명3", LocalDate.of(2022, 9, 30),
			new ArrayList<>());

		Long id1 = milestoneRepository.save(milestone1).getId();
		Long id2 = milestoneRepository.save(milestone2).getId();
		Long id3 = milestoneRepository.save(milestone3).getId();

		// when
		List<Milestone> milestones = milestoneRepository.findAll();

		// then
		assertThat(milestones).hasSize(3);
		assertThat(milestones.get(0)).usingRecursiveComparison()
			.isEqualTo(milestone1);
		assertThat(milestones.get(1)).usingRecursiveComparison()
			.isEqualTo(milestone2);
		assertThat(milestones.get(2)).usingRecursiveComparison()
			.isEqualTo(milestone3);
	}

	@Test
	void 마일스톤을_수정한다_모든필드() {
		// given
		Milestone milestone1 = Milestone.of(null, "제목1", "설명1", LocalDate.of(2022, 7, 10),
			new ArrayList<>());
		milestoneRepository.save(milestone1);
		MilestoneUpdateRequest request = new MilestoneUpdateRequest("수정된 제목1", "수정된 설명1",
			LocalDate.of(2022, 7, 25));

		// when
		milestone1.update(request);
		Long id = milestoneRepository.save(milestone1).getId();

		// then
		Milestone foundMilestone = milestoneRepository.findById(id).get();
		assertThat(foundMilestone).usingRecursiveComparison().isEqualTo(milestone1);
	}

	@Test
	void 마일스톤을_수정한다_일부필드() {
		// given
		Milestone milestone1 = Milestone.of(1L, "제목1", "설명1", LocalDate.of(2022, 7, 10),
			new ArrayList<>());

		milestoneRepository.save(milestone1);
		MilestoneUpdateRequest request = new MilestoneUpdateRequest(null, "수정된 설명1", null);

		// when
		milestone1.update(request);
		Long id = milestoneRepository.save(milestone1).getId();

		// then
		Milestone foundMilestone = milestoneRepository.findById(1L)
			.orElseThrow(MilestoneException::new);
		assertThat(foundMilestone).usingRecursiveComparison().isEqualTo(milestone1);

	}

	@Test
	void 마일스톤을_일괄적으로_삭제한다() {
		// given
		Milestone milestone1 = Milestone.of(null, "제목1", "설명1", LocalDate.of(2022, 7, 10),
			new ArrayList<>());
		Milestone milestone2 = Milestone.of(null, "제목2", "설명2", LocalDate.of(2022, 8, 20),
			new ArrayList<>());
		Milestone milestone3 = Milestone.of(null, "제목3", "설명3", LocalDate.of(2022, 9, 30),
			new ArrayList<>());

		Long id1 = milestoneRepository.save(milestone1).getId();
		Long id2 = milestoneRepository.save(milestone2).getId();
		Long id3 = milestoneRepository.save(milestone3).getId();

		List<Long> milestoneIds = new ArrayList<>(List.of(id1, id3));

		// when
		milestoneRepository.deleteAllByIdInBatch(milestoneIds);
		em.flush();
		em.clear();

		// then
		List<Milestone> milestones = milestoneRepository.findAll();
		Milestone foundMilestone = milestones.get(0);

		assertThat(milestones).hasSize(1);
		assertThat(foundMilestone).usingRecursiveComparison().isEqualTo(milestone2);
		assertThat(milestoneRepository.findById(id1)).isEmpty();
		assertThat(milestoneRepository.findById(id3)).isEmpty();
	}

}
