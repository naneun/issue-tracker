package com.team03.issuetracker.issue.api;

import com.team03.issuetracker.issue.application.LabelService;
import com.team03.issuetracker.issue.domain.dto.label.LabelCreateRequest;
import com.team03.issuetracker.issue.domain.dto.label.LabelModifyRequest;
import com.team03.issuetracker.issue.domain.dto.label.LabelResponse;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/labels")
@RequiredArgsConstructor
public class LabelController {

	private final LabelService labelService;

	@ApiOperation(
		value = "Label 등록",
		notes = "Label 등록한다.",
		produces = "application/json"
	)
	@PostMapping
	public ResponseEntity<LabelResponse> addLabel(@RequestBody LabelCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(labelService.addLabel(request));
	}

	@ApiOperation(
		value = "Label 전체 조회",
		notes = "Label 전체 목록을 조회한다.",
		produces = "application/json"
	)
	@GetMapping
	public ResponseEntity<List<LabelResponse>> findAll() {
		return ResponseEntity.ok(labelService.findAll());
	}

	@ApiOperation(
		value = "Label 수정",
		notes = "Label을 수정한다",
		produces = "application/json"
	)
	@PatchMapping("/{id}")
	public ResponseEntity<LabelResponse> modifyLabel(@PathVariable Long id, @RequestBody LabelModifyRequest request) {
		return ResponseEntity.ok(labelService.modifyLabel(id, request));
	}

	@ApiOperation(
		value = "Label 일괄 삭제",
		notes = "입력된 id에 해당되는 Label들을 일괄 삭제한다.",
		produces = "application/json"
	)
	@DeleteMapping
	public ResponseEntity<List<Long>> deleteById(List<Long> ids) {
		return ResponseEntity.ok(labelService.deleteById(ids));
	}
}
