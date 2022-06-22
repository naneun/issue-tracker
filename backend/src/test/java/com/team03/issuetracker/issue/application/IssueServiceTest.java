package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.repository.IssueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IssueServiceTest {

    final IssueService issueService;
    final IssueRepository issueRepository;

    public IssueServiceTest(IssueService issueService, IssueRepository issueRepository) {
        this.issueService = issueService;
        this.issueRepository = issueRepository;
    }

    @Test
    void 해당_상태_값의_이슈_리스트를_출력한다() {
        // TODO List<IssueSimpleResponse> findByState(IssueState state);
    }

    @Test
    void 이슈를_등록한다() {
        // TODO addIssue(IssueAddRequest issueAddRequest);
    }

    @Test
    void 이슈를_수정한다() {
        // TODO modifyIssue(IssueModifyRequest issueModifyRequest);
    }

    @Test
    void 이슈_상태를_일괄_변경한다() {
        // TODO changeStateById(List<Long> checkedIds);
    }

    @Test
    void 이슈를_일괄_삭제한다() {
        // TODO deleteById(List<Long> checkedIds);
    }

    @Test
    void 상태_작성자_레이블_마일스톤을_검색_조건으로_필터링하여_해당하는_이슈를_조회한다() {
        // TODO List<IssueSimpleResponse> findBySearchCondition(IssueSearchCondition issueSearchCondition);
    }

    @Test
    void 이슈의_제목과_본문_내용에서_검색어가_매칭되는_이슈_리스트를_조회한다() {
        // TODO List<IssueSimpleResponse> findByTextSearch(IssueSearchWord issueSearchWord);
    }
}
