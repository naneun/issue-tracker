package com.team03.issuetracker.issue.application;

import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.issue.domain.dto.issue.request.IssueAddRequest;
import com.team03.issuetracker.issue.domain.dto.issue.request.IssueModifyRequest;
import com.team03.issuetracker.issue.domain.dto.issue.request.IssueSearchCondition;
import com.team03.issuetracker.issue.domain.dto.issue.request.IssueSearchText;
import com.team03.issuetracker.issue.domain.dto.issue.response.IssueDetailResponse;
import com.team03.issuetracker.issue.domain.dto.issue.response.IssueResponse;
import com.team03.issuetracker.issue.domain.dto.issue.response.IssueSimpleResponse;
import java.util.List;

public interface IssueService {

	/**
	 * 해당 상태 값의 이슈 리스트를 출력한다. 안드로이드의 경우 오픈된 이슈 리스트만 출력하도록 한다. state -> 'OPEN'
	 *
	 * @param state
	 * @return
	 */
	List<IssueSimpleResponse> findByState(IssueState state);

	/**
	 * 해당 id 의 이슈 상세 정보를 출력한다.
	 *
	 * @param id
	 * @return
	 */
	IssueDetailResponse findDetailById(Long id);

	/**
	 * 이슈를 등록한다.
	 *
	 * @param issueAddRequest { }
	 */
	IssueResponse addIssue(IssueAddRequest issueAddRequest);

	/**
	 * 이슈를 수정한다.
	 *
	 * @param issueModifyRequest { }
	 */
	IssueResponse modifyIssue(Long issueId, IssueModifyRequest issueModifyRequest);

	/**
	 * 이슈 상태를 일괄 변경한다.
	 *
	 * @param checkedIds
	 */
	List<IssueResponse> changeStateById(List<Long> checkedIds);

	/**
	 * 이슈를 일괄 삭제한다.
	 *
	 * @param checkedIds
	 */
	List<Long> deleteById(List<Long> checkedIds);

	/**
	 * '상태', '작성자', '레이블', '마일스톤' 을 검색 조건으로 필터링하여 해당하는 이슈를 조회한다.
	 *
	 * @param searchCondition { IssueState state; Member creator; Label label; Milestone
	 *                             milestone; }
	 * @return
	 */
	List<IssueSimpleResponse> findBySearchCondition(IssueSearchCondition searchCondition);

	/**
	 * 이슈의 '제목' 과 '본문' 내용에서 '검색어' 가 매칭되는 이슈 리스트를 조회한다.
	 *
	 * @param issueSearchText { String text; }
	 * @return
	 */
	List<IssueSimpleResponse> findByTextSearch(IssueSearchText issueSearchText);
}
