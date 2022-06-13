package com.team03.issuetracker.milestone.repository;

import com.team03.issuetracker.milestone.domain.Milestone;
import com.team03.issuetracker.milestone.dto.MilestoneDeleteRequest;
import com.team03.issuetracker.milestone.dto.MilestoneUpdateRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MilestoneRepositoryTest {

	private final MilestoneRepository milestoneRepository;
	@PersistenceContext
	private EntityManager em;

	@Autowired
	public MilestoneRepositoryTest(MilestoneRepository milestoneRepository) {
		this.milestoneRepository = milestoneRepository;
	}

	@Test
	public void 마일스톤을_생성한다() {
		// given
		Milestone milestone = new Milestone(null, "제목", "설명", LocalDate.of(2022, 6, 30));

		// when
		Long id = milestoneRepository.save(milestone).getId();

		// then
		Milestone foundMilestone = milestoneRepository.findById(id).get();
		assertThat(foundMilestone.getTitle()).isEqualTo("제목");
		assertThat(foundMilestone.getDescription()).isEqualTo("설명");
		assertThat(foundMilestone.getDueDate()).isEqualTo("2022-06-30");

	}

	@Test
	public void 마일스톤_목록을_조회한다() {
		// given
		Milestone milestone1 = new Milestone(null, "제목1", "설명1", LocalDate.of(2022, 7, 10));
		Milestone milestone2 = new Milestone(null, "제목2", "설명2", LocalDate.of(2022, 8, 20));
		Milestone milestone3 = new Milestone(null, "제목3", "설명3", LocalDate.of(2022, 9, 30));

		Long id1 = milestoneRepository.save(milestone1).getId();
		Long id2 = milestoneRepository.save(milestone2).getId();
		Long id3 = milestoneRepository.save(milestone3).getId();

		// when
		List<Milestone> milestones = milestoneRepository.findAll();

		// then
		assertThat(milestones).hasSize(3);
		assertThat(milestoneRepository.findById(id1).get().getTitle()).isEqualTo("제목1");
		assertThat(milestoneRepository.findById(id2).get().getTitle()).isEqualTo("제목2");
		assertThat(milestoneRepository.findById(id3).get().getTitle()).isEqualTo("제목3");
	}

	@Test
	public void 마일스톤을_수정한다_모든필드() {
		// given
		Milestone milestone1 = new Milestone(null, "제목1", "설명1", LocalDate.of(2022, 7, 10));
		Milestone savedMileStone = milestoneRepository.save(milestone1);
		MilestoneUpdateRequest request = new MilestoneUpdateRequest("수정된 제목1", "수정된 설명1",
			LocalDate.of(2022, 7, 25));

		// when
		savedMileStone.update(request);
		Long id = milestoneRepository.save(savedMileStone).getId();

		// then
		Milestone foundMilestone = milestoneRepository.findById(id).get();
		assertThat(foundMilestone.getId()).isEqualTo(id);
		assertThat(foundMilestone.getTitle()).isEqualTo("수정된 제목1");
		assertThat(foundMilestone.getDescription()).isEqualTo("수정된 설명1");
		assertThat(foundMilestone.getDueDate()).isEqualTo("2022-07-25");
	}

	@Test
	public void 마일스톤을_수정한다_일부필드() {
		// given
		Milestone milestone1 = new Milestone(null, "제목1", "설명1", LocalDate.of(2022, 7, 10));
		Milestone savedMileStone = milestoneRepository.save(milestone1);
		MilestoneUpdateRequest request = new MilestoneUpdateRequest(null, "수정된 설명1", null);

		// when
		savedMileStone.update(request);
		Long id = milestoneRepository.save(savedMileStone).getId();

		// then
		Milestone foundMilestone = milestoneRepository.findById(id).get();
		assertThat(foundMilestone.getId()).isEqualTo(id);
		assertThat(foundMilestone.getTitle()).isEqualTo("제목1");
		assertThat(foundMilestone.getDescription()).isEqualTo("수정된 설명1");
		assertThat(foundMilestone.getDueDate()).isEqualTo("2022-07-10");
	}

	@Test
	public void 마일스톤을_일괄적으로_삭제한다() {
		// given
		Milestone milestone1 = new Milestone(null, "제목1", "설명1", LocalDate.of(2022, 7, 10));
		Milestone milestone2 = new Milestone(null, "제목2", "설명2", LocalDate.of(2022, 8, 20));
		Milestone milestone3 = new Milestone(null, "제목3", "설명3", LocalDate.of(2022, 9, 30));

		Long id1 = milestoneRepository.save(milestone1).getId();
		Long id2 = milestoneRepository.save(milestone2).getId();
		Long id3 = milestoneRepository.save(milestone3).getId();

		MilestoneDeleteRequest request = new MilestoneDeleteRequest(
			new ArrayList<>(List.of(id1, id3)));
		List<Long> milestoneIds = request.getIds();

		// when
		milestoneRepository.deleteAllByIdInBatch(milestoneIds);
		em.flush();
		em.clear();

		// then
		List<Milestone> milestones = milestoneRepository.findAll();
		Milestone foundMilestone = milestones.get(0);

		assertThat(milestones).hasSize(1);
		assertThat(foundMilestone.getId()).isEqualTo(id2);
		assertThat(milestoneRepository.findById(id1)).isEmpty();
		assertThat(milestoneRepository.findById(id3)).isEmpty();
	}

	// Todo : 마일스톤 열린이슈/닫힌이슈 표시되는 것 테스트
	// Todo : 마일스톤 완료도 표시되는 것 테스트
}
