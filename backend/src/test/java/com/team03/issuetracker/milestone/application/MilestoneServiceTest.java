package com.team03.issuetracker.milestone.application;

import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.milestone.application.impl.MilestoneServiceImpl;
import com.team03.issuetracker.milestone.domain.Milestone;
import com.team03.issuetracker.milestone.domain.dto.MilestoneCreateRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneModifyRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneResponse;
import com.team03.issuetracker.milestone.exception.MilestoneException;
import com.team03.issuetracker.milestone.repository.MilestoneRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class MilestoneServiceTest {

	final MilestoneServiceImpl milestoneServiceImpl;

	final MilestoneRepository milestoneRepository;

	@Autowired
	public MilestoneServiceTest(MilestoneServiceImpl milestoneServiceImpl, MilestoneRepository milestoneRepository) {
		this.milestoneServiceImpl = milestoneServiceImpl;
		this.milestoneRepository = milestoneRepository;

	}

	private Long getIssueCount(Long milestoneId, IssueState state) {
		return milestoneRepository.findById(milestoneId).stream()
			.map(Milestone::getIssues)
			.flatMap(Collection::stream)
			.filter(issue -> issue.getState().equals(state))
			.count();

	}

	@Test
	void 마일스톤을_생성한다() {

		// given
		Long id = 1L;
		MilestoneCreateRequest createRequest = new MilestoneCreateRequest("제목", "마일스톤에 대한 설명",
			LocalDate.of(2022, 7, 1));
		Milestone registeredMilestone = milestoneRepository.findById(id).orElseThrow();

		// when
		MilestoneResponse milestoneResponse = milestoneServiceImpl.addMilestone(createRequest);

		// then
		assertAll(
			() -> assertThat(milestoneResponse).usingRecursiveComparison()
				.ignoringFields("openIssueCount", "closedIssueCount")
				.isEqualTo(registeredMilestone),
			() -> assertThat(milestoneResponse.getOpenIssueCount()).isEqualTo(0L),
			() -> assertThat(milestoneResponse.getClosedIssueCount()).isEqualTo(0L)
		);
	}

	@Test
	@Transactional
	void 마일스톤_목록을_조회하면_DTO를_반환한다() {

		// given
		List<Milestone> savedMilestones = milestoneRepository.findAll();

		// when
		List<MilestoneResponse> milestoneDtos = milestoneServiceImpl.findAll();

		// then
		milestoneDtos.forEach(milestoneDto -> {
			assertThat(milestoneDto).usingRecursiveComparison()
				.ignoringFields("openIssueCount", "closedIssueCount")
				.isEqualTo(savedMilestones.get(milestoneDtos.indexOf(milestoneDto)));
		});

		milestoneDtos.forEach(milestoneDto -> {
			assertAll(
				() -> assertThat(milestoneDto.getOpenIssueCount()).isEqualTo(
					getIssueCount(milestoneDtos.indexOf(milestoneDto) + 1L, IssueState.OPEN)),
				() -> assertThat(milestoneDto.getClosedIssueCount()).isEqualTo(
					getIssueCount(milestoneDtos.indexOf(milestoneDto) + 1L, IssueState.CLOSE))
			);
		});
	}

	@Test
	void 마일스톤을_편집한다() {

		// given
		Long id = 1L;
		MilestoneModifyRequest request = new MilestoneModifyRequest("수정 제목", "수정 설명",
			LocalDate.of(2022, 8, 1));
		Milestone registeredMilestone = milestoneRepository.findById(id).orElseThrow(MilestoneException::new);
		Milestone updatedMilestone = Milestone.of(id, "수정 제목", "수정 설명", LocalDate.of(2022, 8, 1),
			new ArrayList<>());

		// when
		MilestoneResponse milestoneResponse = milestoneServiceImpl.update(id, request);

		// then
		assertThat(milestoneResponse).usingRecursiveComparison()
			.ignoringFields("openIssueCount", "closedIssueCount")
			.isEqualTo(updatedMilestone);
	}

	// Todo : 삭제 테스트는 Transactional을 붙여주지 않으면 실패한다. 왜일까..
	// "object references an unsaved transient instance"
	@Test
	@Transactional
	void 마일스톤을_일괄_삭제한다() {

		// given
		List<Long> ids = List.of(1L, 2L);
		List<Milestone> foundMilestones = milestoneRepository.findAllById(ids);

		// when
		List<Long> deletedMilestoneIds = milestoneServiceImpl.deleteById(ids);

		// then
		assertThat(foundMilestones).hasSize(2);
		deletedMilestoneIds.forEach(id ->
			assertThat(milestoneRepository.findById(id)).isEmpty()
		);

	}
}
