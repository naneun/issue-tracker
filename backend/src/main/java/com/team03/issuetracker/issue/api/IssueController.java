package com.team03.issuetracker.issue.api;

import com.team03.issuetracker.issue.application.IssueService;
import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.issue.domain.dto.issue.IssueAddRequest;
import com.team03.issuetracker.issue.domain.dto.issue.IssueModifyRequest;
import com.team03.issuetracker.issue.domain.dto.issue.IssueResponse;
import com.team03.issuetracker.issue.domain.dto.issue.IssueSimpleResponse;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IssueController {

	private final IssueService issueService;

	@ApiOperation(
		value = "상태 값을 조건으로 이슈 리스트를 조회",
		notes = "상태 값을 조건으로 이슈 리스트를 조회한다.",
		produces = "application/json",
		response = IssueSimpleResponse.class
	)
	@GetMapping("/api/issues")
	public ResponseEntity<List<IssueSimpleResponse>> findByState(IssueState state) {
		return ResponseEntity.ok(issueService.findByState(state));
	}

	@ApiOperation(
		value = "이슈 등록",
		notes = "이슈를 등록한다.",
		produces = "application/json",
		response = IssueResponse.class
	)
	@PostMapping("/api/issues")
	public ResponseEntity<IssueResponse> addIssue(@RequestBody IssueAddRequest issueAddRequest) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(issueService.addIssue(issueAddRequest));
	}

	@ApiOperation(
		value = "이슈 수정",
		notes = "이슈를 수정한다.",
		produces = "application/json",
		response = IssueResponse.class
	)
	@PatchMapping("/api/issues")
	public ResponseEntity<IssueResponse> modifyIssue(
		@RequestBody IssueModifyRequest issueModifyRequest) {

		return ResponseEntity.ok().body(issueService.modifyIssue(issueModifyRequest));
	}

	@ApiOperation(
		value = "이슈 상태 일괄 변경",
		notes = "이슈의 상태를 일괄 변경한다.",
		produces = "application/json",
		response = IssueResponse.class
	)
	@PatchMapping("/api/issues/state")
	public ResponseEntity<List<IssueResponse>> changeStateById(@RequestParam("id") List<Long> checkedIds) {
		return ResponseEntity.ok().body(issueService.changeStateById(checkedIds));
	}

	@ApiOperation(
		value = "이슈 일괄 삭제",
		notes = "이슈를 일괄 삭제한다.",
		produces = "application/json",
		response = IssueResponse.class
	)
	@DeleteMapping("/api/issues")
	public ResponseEntity<List<Long>> deleteById(@RequestParam("id") List<Long> checkedIds) {
		return ResponseEntity.ok().body(issueService.deleteById(checkedIds));
	}
}
