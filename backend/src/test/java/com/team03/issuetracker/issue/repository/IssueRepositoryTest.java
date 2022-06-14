package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.issue.exception.IssueException;
import com.team03.issuetracker.milestone.domain.Milestone;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
class IssueRepositoryTest {

    final IssueRepository issueRepository;

    static List<Issue> registeredOpenedIssues = List.of(
            Issue.of(1, "title1", "description1", label1, milestone1, assignee1, OPEN),
            Issue.of(2, "title2", "description2", label2, milestone2, assignee2, OPEN),
            Issue.of(3, "title3", "description3", label3, milestone3, assignee3, OPEN)
    );

    static List<Issue> registeredClosedIssues = List.of(
            Issue.of(4, "title4", "description4", label4, milestone4, assignee4, CLOSE),
            Issue.of(5, "title5", "description5", label5, milestone5, assignee5, CLOSE),
            Issue.of(6, "title6", "description6", label6, milestone6, assignee6, CLOSE)
    );

    static List<Issue> registeredIssues;

    @Autowired
    IssueRepositoryTest(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @BeforeAll
    static void beforeAll() {
        registeredIssues.addAll(registeredOpenedIssues);
        registeredIssues.addAll(registeredClosedIssues);
    }

    @Test
    void 오픈된_모든_이슈를_조회한다() {
        // given

        // when
        List<Issue> foundOpenedIssues = issueRepository.findByState(OPEN);

        // then
        foundOpenedIssues.forEach((issue) -> assertThat(issue)
                .usingRecursiveComparison()
                .isEqualTo(registeredOpenedIssues.get(foundOpenedIssues.indexOf(issue))));

        // TODO IssueState { OPEN, CLOSE }
        // TODO SimpleIssueResponse { Milestone, title, description, Label }
    }

    @Test
    void 해당하는_ID를_가진_이슈를_닫는다() {
        // TODO '해당하는 ID를 가진 이슈의 상태를 변경한다' 로 수정될 수 있다.

        // given
        Long id = 1L;
        Issue foundIssue = issueRepository.findById(id)
                .orElseThrow(IssueException::new);

        // when
        foundIssue.close();
        Issue changedIssue = issueRepository.save(foundIssue);

        // then
        assertAll(
                () -> assertThat(changedIssue.getState()).isEqualTo(CLOSE),
                () -> assertThat(changedIssue).usingRecursiveComparison().isEqualTo(foundIssue)
        );
    }

    /**
     * @implNote 해당하는_ID를_가진_이슈의_상세정보를_조회한다
     */
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6})
    void 해당하는_ID를_가진_이슈를_조회한다(Long id) {
        // given

        // when
        Issue foundIssue = issueRepository.findById(id)
                .orElseThrow(IssueException::new);

        // then
        assertThat(foundIssue).usingRecursiveComparison()
                .isEqualTo(registeredIssues.get(id.intValue() - 1));

        // TODO IssueCardResponse { 레이블, 마일스톤, 담당자, (상태) }
    }

    @Test
    void 이슈상태_작성자_레이블_마일스톤을_검색조건으로_필터링하여_해당하는_이슈를_조회한다() {
        // given
        IssueState state = OPEN;
        Long creatorId = 1L;
        Long labelId = 1L;
        Long milestoneId = 1L;

        IssueSearchCondition issueSearchCondition = IssueSearchCondition.of(state, creatorId, labelId, milestoneId);

        // when
        List<Issue> foundIssues = issueRepository.findBySearchCondition(issueSearchCondition);

        // then
        foundIssues.forEach(issue -> assertAll(
                        assertThat(issue.getState()).isEqualTo(creatorId),
                        assertThat(issue.getCreator().getId()).isEqualTo(creatorId),
                        assertThat(issue.getLabel().getId()).isEqualTo(labelId),
                        assertThat(issue.getMilestonr().getId()).isEqualTo(milestoneId)
                )
        );

        // TODO Issue { 제목, 설명, 작성자, 레이블, 마일스톤, 담당자, 상태 }
    }

    @Test
    void 검색어로_주어진_문자열이_제목이나_설명에_포함되어있는_이슈를_조회한다() {
        // TODO using ELK
    }

    @Test
    void 리스트로_주어진_ID에_해당하는_이슈들을_일괄적으로_닫는다() {
        // given
        List<Issue> foundOpenedIssues = issueRepository.findByState(OPEN);
        foundOpenedIssues.forEach(Issue::close);
        issueRepository.saveAll(foundOpenedIssues);

        // when
        List<Issue> foundClosedIssues = issueRepository.findByState(CLOSE);

        // then
        assertThat(foundClosedIssues).containsExactlyElementsOf(foundOpenedIssues);
    }

    @Test
    void 리스트로_주어진_ID에_해당하는_이슈들을_일괄적으로_삭제한다() {
        // TODO '삭제된 이슈에 포함된 댓글들 또한 모두 삭제되었는지 확인해야한다.'

        // given
        List<Issue> foundIssues = issueRepository.findAll();
        List<Long> issueIds = foundIssues.stream()
                .map(Issue::getId)
                .collect(Collectors.toList());

        // when
        issueRepository.deleteAllByIdInBatch(issueIds);

        // then
        assertAll(
                () -> assertThat(foundIssues).isNotEmpty(),
                () -> assertThat(issueRepository.findAll()).isEmpty()
        );
    }

    @Test
    void 새_이슈를_등록한다() {
        // TODO Issue { 제목(필수), 코멘트(필수), (작성자, 작성시간, 수정자, 수정시간), 레이블, 마일스톤, 담당자 }

        // given
        Issue newIssue = Issue.of("title7", "description7", creator, label, milestone, assignee);

        // when
        issueRepository.save(newIssue);
        Issue foundIssue = issueRepository.findById(newIssue.getId())
                .orElseThrow(IssueException::new);

        // then
        assertAll(() -> assertThat(foundIssue).usingRecursiveComparison().isEqualTo(newIssue));
    }

    @Test
    void 등록된_이슈를_수정한다() {
        // TODO @Modifier, @ModifiedDate

        // given
        Long id = 1L;
        Issue foundIssue = issueRepository.findById(id)
                .orElseThrow(IssueException::new);

        // when
        String otherTitle = "otherTitle";
        String otherDescription = "otherDescription";
        Label otherLabel;
        Milestone otherMilestone;
        Member otherMember;
        Member modifier;

        foundIssue.changeTitle(otherTitle);
        foundIssue.changeDescription(otherDescription);
        foundIssue.changeLabel(otherLabel);
        foundIssue.changeMilestone(otherMilestone);
        foundIssue.changeAssignee(otherMember);

        Issue changedIssue = issueRepository.save(foundIssue);

        // then
        assertThat(changedIssue).usingRecursiveComparison().isEqualTo(foundIssue);
    }
}
