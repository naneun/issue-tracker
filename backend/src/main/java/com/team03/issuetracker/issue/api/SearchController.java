package com.team03.issuetracker.issue.api;

import com.team03.issuetracker.issue.application.IssueService;
import com.team03.issuetracker.issue.domain.dto.issue.request.IssueSearchCondition;
import com.team03.issuetracker.issue.domain.dto.issue.response.IssueSimpleResponse;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

	private final IssueService issueService;

	@ApiOperation(
		value = "필터 조건으로 이슈 리스트를 조회",
		notes = "필터 조건으로 이슈 리스트를 조회한다.",
		produces = "application/json",
		response = IssueSimpleResponse.class
	)
	@GetMapping("/issues")
	public ResponseEntity<List<IssueSimpleResponse>> findByState(
		IssueSearchCondition searchCondition) {
		return ResponseEntity.ok(issueService.findBySearchCondition(searchCondition));
	}
}
