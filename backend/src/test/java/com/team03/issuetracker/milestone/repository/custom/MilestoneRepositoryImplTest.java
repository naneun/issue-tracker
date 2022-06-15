package com.team03.issuetracker.milestone.repository.custom;

import com.team03.issuetracker.common.config.DataJpaConfig;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.milestone.domain.Milestone;
import com.team03.issuetracker.milestone.domain.dto.MilestoneData;
import com.team03.issuetracker.milestone.repository.MilestoneRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(DataJpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MilestoneRepositoryImplTest {

	MilestoneRepository milestoneRepository;

	@Autowired
	public MilestoneRepositoryImplTest(
		MilestoneRepository milestoneRepository) {
		this.milestoneRepository = milestoneRepository;
	}

	@Test
	void 마일스톤_목록_조회_시_해당_마일스톤에_등록된_열린이슈_및_닫힌이슈_개수가_표시된다() {
		// given

		Milestone milestone = milestoneRepository.findById(1L).get();
		String title = milestone.getTitle();

		Issue closedIssue = Issue.builder().id(1L).build();
		closedIssue.changeState();
		Issue openIssue = Issue.builder().id(2L).build();

		milestone.addIssue(closedIssue);
		milestone.addIssue(openIssue);
		milestoneRepository.save(milestone);

		// when
		List<MilestoneData> milestonesDataList = milestoneRepository.findAllMilestones();
		MilestoneData milestoneData = milestonesDataList.stream()
			.filter(data -> data.getTitle().equals(title))
			.findFirst()
			.get();

		// then
		assertThat(milestoneData.getClosedIssueCount()).isEqualTo(1);
		assertThat(milestoneData.getOpenIssueCount()).isEqualTo(1);
	}
}
