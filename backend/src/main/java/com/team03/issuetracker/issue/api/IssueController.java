package com.team03.issuetracker.issue.api;

import com.team03.issuetracker.issue.application.CommentService;
import com.team03.issuetracker.issue.application.IssueService;
import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.issue.domain.dto.comment.request.CommentAddRequest;
import com.team03.issuetracker.issue.domain.dto.comment.request.CommentModifyRequest;
import com.team03.issuetracker.issue.domain.dto.comment.response.CommentResponse;
import com.team03.issuetracker.issue.domain.dto.comment.response.CommentSimpleResponse;
import com.team03.issuetracker.issue.domain.dto.issue.request.IssueAddRequest;
import com.team03.issuetracker.issue.domain.dto.issue.request.IssueModifyRequest;
import com.team03.issuetracker.issue.domain.dto.issue.response.IssueDetailResponse;
import com.team03.issuetracker.issue.domain.dto.issue.response.IssueResponse;
import com.team03.issuetracker.issue.domain.dto.issue.response.IssueSimpleResponse;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

	private final IssueService issueService;
	private final CommentService commentService;

	@ApiOperation(
		value = "상태 값을 조건으로 이슈 리스트를 조회",
		notes = "상태 값을 조건으로 이슈 리스트를 조회한다.",
		produces = "application/json",
		response = IssueSimpleResponse.class
	)
	@GetMapping
	public ResponseEntity<List<IssueSimpleResponse>> findByState(IssueState state) {
		return ResponseEntity.ok(issueService.findByState(state));
	}

	@ApiOperation(
		value = "해당 ID 의 이슈 상세 정보 조회",
		notes = "해당 ID 의 이슈 상세 정보를 조회한다.",
		produces = "application/json",
		response = IssueDetailResponse.class
	)
	@GetMapping("/{id}")
	public ResponseEntity<IssueDetailResponse> findDetailById(@PathVariable Long id) {
		return ResponseEntity.ok(issueService.findDetailById(id));
	}

	@ApiOperation(
		value = "이슈 등록",
		notes = "이슈를 등록한다.",
		produces = "application/json",
		response = IssueResponse.class
	)
	@PostMapping
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
	@PatchMapping("/{id}")
	public ResponseEntity<IssueResponse> modifyIssue(@PathVariable Long id,
		@RequestBody IssueModifyRequest issueModifyRequest) {

		return ResponseEntity.ok(issueService.modifyIssue(id, issueModifyRequest));
	}

	@ApiOperation(
		value = "이슈 상태 일괄 변경",
		notes = "이슈의 상태를 일괄 변경한다.",
		produces = "application/json",
		response = IssueResponse.class
	)
	@PatchMapping("/state")
	public ResponseEntity<List<IssueResponse>> changeState(
		@RequestParam("id") List<Long> checkedIds) {

		return ResponseEntity.ok(issueService.changeState(checkedIds));
	}

	@ApiOperation(
		value = "이슈 일괄 삭제",
		notes = "이슈를 일괄 삭제한다.",
		produces = "application/json",
		response = IssueResponse.class
	)
	@DeleteMapping
	public ResponseEntity<List<Long>> deleteById(@RequestParam("id") List<Long> checkedIds) {
		return ResponseEntity.ok(issueService.deleteById(checkedIds));
	}

	@ApiOperation(
		value = "해당 이슈에 등록된 댓글 리스트 조회",
		notes = "해당 이슈에 등록된 댓글 리스트를 조회한다.",
		produces = "application/json",
		response = CommentSimpleResponse.class
	)
	@GetMapping("/{issueId}/comments")
	public ResponseEntity<Page<CommentSimpleResponse>> findByIssueId(@PathVariable Long issueId,
		Pageable pageable) {

		return ResponseEntity.ok(commentService.findByIssueId(issueId, pageable));
	}

	@ApiOperation(
		value = "해당 이슈에 댓글 등록",
		notes = "해당 이슈에 댓글을 등록한다.",
		produces = "application/json",
		response = CommentResponse.class
	)
	@PostMapping("/{issueId}/comments")
	public ResponseEntity<CommentResponse> addComment(@PathVariable Long issueId,
		@RequestBody CommentAddRequest commentAddRequest) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(commentService.addComment(issueId, commentAddRequest));
	}

	@ApiOperation(
		value = "해당 이슈에 댓글 수정",
		notes = "해당 이슈에 댓글을 수정한다.",
		produces = "application/json",
		response = CommentResponse.class
	)
	@PostMapping("/{issueId}/comments/{commentId}")
	public ResponseEntity<CommentResponse> modifyComment(@PathVariable Long issueId,
		@PathVariable Long commentId,
		@RequestBody CommentModifyRequest commentModifyRequest) {

		return ResponseEntity.ok(
			commentService.modifyComment(issueId, commentId, commentModifyRequest));
	}

	@ApiOperation(
		value = "해당 이슈에 댓글 삭제",
		notes = "해당 이슈에 댓글을 삭제한다.",
		produces = "application/json",
		response = CommentResponse.class
	)
	@DeleteMapping("/{issueId}/comments/{commentId}")
	public ResponseEntity<CommentResponse> deleteComment(@PathVariable Long issueId,
		@PathVariable Long commentId) {

		return ResponseEntity.ok(commentService.deleteComment(issueId, commentId));
	}
}
