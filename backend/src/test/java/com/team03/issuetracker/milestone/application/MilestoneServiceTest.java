package com.team03.issuetracker.milestone.application;

import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.milestone.domain.Milestone;
import com.team03.issuetracker.milestone.domain.dto.MilestoneCreateRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneModifyRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneResponse;
import com.team03.issuetracker.milestone.repository.MilestoneRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@WebMvcTest(MilestoneService.class)
class MilestoneServiceTest {

	final EntityManager entityManager;

	final MilestoneService milestoneService;

	final List<Milestone> registeredMilestones;

	@MockBean
	MilestoneRepository milestoneRepository;

	@Autowired
	MilestoneServiceTest(MilestoneService milestoneService, EntityManager entityManager) {
		this.milestoneService = milestoneService;
		this.entityManager = entityManager;

		this.registeredMilestones = List.of(
			Milestone.of(1L, "제목", "마일스톤에 대한 설명", LocalDate.of(2022, 07, 01), getIssuesByMilestoneId(1L)),
			Milestone.of(2L, "마일스톤", "코드스쿼드 마일스톤", LocalDate.of(2022, 06, 20), getIssuesByMilestoneId(2L)
			));
	}

	private List<Issue> getIssuesByMilestoneId(Long milestoneId) {
		return entityManager.createQuery(
				"select i from Issue i where i.milestone.id = :milestoneId", Issue.class)
			.setParameter("milestoneId", milestoneId)
			.getResultList();
	}

	private Long getIssueCount(Long milestoneId, IssueState state) {
		return registeredMilestones.get(milestoneId.intValue() - 1)
			.getIssues()
			.stream()
			.filter(issue -> issue.getState().equals(state))
			.count();
	}

	@Test
	void 마일스톤을_생성한다() {

		// given
		MilestoneCreateRequest createRequest = new MilestoneCreateRequest("제목", "마일스톤에 대한 설명",
			LocalDate.of(2022, 7, 1));
		Milestone registeredMilestone = registeredMilestones.get(0);
		given(milestoneRepository.save(createRequest.toEntity())).willReturn(registeredMilestone);

		// when
		Milestone milestone = milestoneService.addMilestone(createRequest);

		// then
		assertThat(milestone).usingRecursiveComparison().isEqualTo(registeredMilestone);
	}

	@Test
	void 마일스톤_목록을_조회하면_DTO를_반환한다() {

		// given
		given(milestoneRepository.findAll()).willReturn(registeredMilestones);

		// when
		List<MilestoneResponse> milestoneDtos = milestoneService.findAll();

		// then
		milestoneDtos.forEach(milestoneDto -> {
			assertThat(milestoneDto).usingRecursiveComparison()
				.ignoringFields("openIssueCount", "closedIssueCount")
				.isEqualTo(registeredMilestones.get(milestoneDtos.indexOf(milestoneDto)));
		});

		milestoneDtos.forEach(milestoneDto -> {
			assertAll(
				() -> assertThat(milestoneDto.getOpenIssueCount()).isEqualTo(
					getIssueCount(milestoneDtos.indexOf(milestoneDto) + 1L, IssueState.OPEN)),
				() -> assertThat(milestoneDto.getOpenIssueCount()).isEqualTo(
					getIssueCount(milestoneDtos.indexOf(milestoneDto) + 1L, IssueState.CLOSE))
			);
		});
	}

	@Test
	void 마일스톤을_편집한다() {

		// given
		MilestoneModifyRequest request = new MilestoneModifyRequest("수정 제목", "수정 설명", LocalDate.of(2022, 8, 1));
		Milestone registeredMilestone = registeredMilestones.get(0);
		Milestone updatedMilestone = Milestone.of(1L, "수정 제목", "수정 설명", LocalDate.of(2022, 8, 1), new ArrayList<>());
		given(milestoneRepository.save(registeredMilestone.update(request))).willReturn(updatedMilestone);

		// when
		Milestone milestone = milestoneService.update(request);

		// then
		assertThat(milestone).usingRecursiveComparison().isEqualTo(updatedMilestone);
	}

	@Test
	void 마일스톤을_일괄_삭제한다() {

		// given

		// when

		// then

	}

}
