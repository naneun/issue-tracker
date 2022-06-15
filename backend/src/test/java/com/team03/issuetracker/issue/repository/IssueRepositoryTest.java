package com.team03.issuetracker.issue.repository;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.issue.domain.Issue;
import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.issue.domain.Label;
import com.team03.issuetracker.issue.domain.dto.IssueSearchCondition;
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

import static com.team03.issuetracker.issue.domain.IssueState.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
class IssueRepositoryTest {

    final IssueRepository issueRepository;

    static List<Issue> registeredOpenedIssues = List.of(
            Issue.of(1L, "title1", "description1", null, null, null),
            Issue.of(2L, "title2", "description2", null, null, null),
            Issue.of(3L, "title3", "description3", null, null, null)
    );

    static List<Issue> registeredClosedIssues = List.of(
            Issue.of(4L, "title4", "description4", null, null, null),
            Issue.of(5L, "title5", "description5", null, null, null),
            Issue.of(6L, "title6", "description6", null, null, null)
    );

    static List<Issue> registeredIssues;

    @Autowired
    IssueRepositoryTest(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @BeforeAll
    static void beforeAll() {
        registeredIssues.addAll(registeredOpenedIssues);

        registeredClosedIssues.forEach(Issue::changeState);
        registeredIssues.addAll(registeredClosedIssues);
    }

    /**
     *
     * @implNote
     * - 사용자에게 '오픈'되어있는 이슈를 보여준다.
     * - IssueSimpleResponse { Milestone, title, description, Label }
     *
     */
    @Test
    @Transactional
    void 오픈된_모든_이슈를_조회한다() {

        // given

        // when
        List<Issue> foundOpenedIssues = issueRepository.findByState(OPEN);

        // then
        foundOpenedIssues.forEach((issue) -> assertThat(issue)
                .usingRecursiveComparison()
                .isEqualTo(registeredOpenedIssues.get(foundOpenedIssues.indexOf(issue))));
    }

    /**
     * @implNote
     * - 클라이언트가 닫기 버튼을 클릭하면 이슈가 닫히고, 실행 취소를 하면 이슈가 다시 열린다.
     * - '해당하는 ID를 가진 이슈를 닫는다' -> '해당하는 ID를 가진 이슈의 상태를 변경한다' 로 수정한다. - 2022/06/15
     *
     */
    @Test
    @Transactional
    void 해당하는_ID를_가진_이슈의_상태를_변경한다() {

        // given
        Long id = 1L;
        Issue foundIssue = issueRepository.findById(id)
                .orElseThrow(IssueException::new);

        // when
        foundIssue.changeState();
        Issue changedIssue = issueRepository.save(foundIssue);

        // then
        assertAll(
                () -> assertThat(changedIssue.getState()).isEqualTo(CLOSE),
                () -> assertThat(changedIssue).usingRecursiveComparison().isEqualTo(foundIssue)
        );
    }

    /**
     * @implNote
     * - 해당하는 ID를 가진 이슈의 상세정보를 조회한다.
     * - IssueDetailResponse { 레이블, 마일스톤, 담당자, (상태) }
     *
     */
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6})
    @Transactional
    void 해당하는_ID를_가진_이슈를_조회한다(Long id) {

        // given

        // when
        Issue foundIssue = issueRepository.findById(id)
                .orElseThrow(IssueException::new);

        // then
        assertThat(foundIssue).usingRecursiveComparison()
                .isEqualTo(registeredIssues.get(id.intValue() - 1));

    }

    /**
     * @implNote
     * - Issue { 제목, 설명, 작성자, 레이블, 마일스톤, 담당자, 상태 }
     *
     */
    @Test
    @Transactional
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
        foundIssues.forEach(issue ->
                assertAll(
                        () -> assertThat(issue.getState()).isEqualTo(state),
                        () -> assertThat(issue.getCreator().getId()).isEqualTo(creatorId),
                        () -> assertThat(issue.getLabel().getId()).isEqualTo(labelId),
                        () -> assertThat(issue.getMilestone().getId()).isEqualTo(milestoneId)
                )
        );
    }

    @Test
    @Transactional
    void 검색어로_주어진_문자열이_제목이나_설명에_포함되어있는_이슈를_조회한다() {

        // TODO 'using ELK'
    }

    /**
     * @implNote
     * - 클라이언트가 체크한 모든 이슈들을 일괄적으로 닫습니다.
     *
     */
    @Test
    @Transactional
    void 리스트로_주어진_ID에_해당하는_이슈들을_일괄적으로_닫는다() {

        // TODO 'List<Long> issueIds -> List<Issue> issues'

        // given
        List<Issue> foundOpenedIssues = issueRepository.findByState(OPEN);
        foundOpenedIssues.forEach(Issue::changeState);
        issueRepository.saveAll(foundOpenedIssues);

        // when
        List<Issue> foundClosedIssues = issueRepository.findByState(CLOSE);

        // then
        assertThat(foundClosedIssues).containsExactlyElementsOf(foundOpenedIssues);
    }

    /**
     * @implNote
     * - 클라이언트가 체크한 모든 이슈들을 일괄적으로 삭제합니다.
     *
     */
    @Test
    @Transactional
    void 리스트로_주어진_ID에_해당하는_이슈들을_일괄적으로_삭제한다() {

        // TODO '삭제된 이슈에 포함된 댓글들 또한 모두 삭제되었는 지 확인해야한다.'

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

    /**
     * @implNote
     * - Issue { 제목(필수), 코멘트(필수), (작성자, 작성시간, 수정자, 수정시간), 레이블, 마일스톤, 담당자 }
     *
     */
    @Test
    @Transactional
    void 새_이슈를_등록한다() {

        // TODO '제목', '코멘트' 필수 값 입력 검증 @NotBlank

        // given
        String title = "title" + registeredIssues.size();
        String content = "content" + registeredIssues.size();

        Label label = null;
        Milestone milestone = null;
        Member assignee = null;

        // TODO 'creator' 등록 검증
        Issue newIssue = Issue.of(null, title, content, label, milestone, assignee);

        // when
        issueRepository.save(newIssue);
        Issue foundIssue = issueRepository.findById(newIssue.getId())
                .orElseThrow(IssueException::new);

        // then
        assertAll(() -> assertThat(foundIssue).usingRecursiveComparison().isEqualTo(newIssue));
    }

    @Test
    @Transactional
    void 등록된_이슈를_수정한다() {

        // TODO '수정자', '수정시간' (@LastModifiedBy, @LastModifiedDate) 검증

        // given
        Long id = 1L;
        Issue foundIssue = issueRepository.findById(id)
                .orElseThrow(IssueException::new);

        // when
        String otherTitle = "otherTitle";
        String otherContent = "otherContent";
        Label otherLabel = null;
        Milestone otherMilestone = null;
        Member otherAssignee = null;

        foundIssue.changeTitle(otherTitle);
        foundIssue.changeContent(otherContent);
        foundIssue.changeLabel(otherLabel);
        foundIssue.changeMilestone(otherMilestone);
        foundIssue.changeAssignee(otherAssignee);

        Issue changedIssue = issueRepository.findById(foundIssue.getId())
                .orElseThrow(IssueException::new);

        // then
        assertThat(changedIssue).usingRecursiveComparison().isEqualTo(foundIssue);
    }
}
