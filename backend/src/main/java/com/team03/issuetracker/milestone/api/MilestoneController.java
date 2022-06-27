package com.team03.issuetracker.milestone.api;

import com.team03.issuetracker.milestone.application.MilestoneService;
import com.team03.issuetracker.milestone.domain.dto.MilestoneCreateRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneModifyRequest;
import com.team03.issuetracker.milestone.domain.dto.MilestoneResponse;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/milestones")
@RequiredArgsConstructor
public class MilestoneController {

	private final MilestoneService milestoneService;

	@ApiOperation(
		value = "Milestone 등록",
		notes = "Milestone을 등록한다.",
		produces = "application/json"
	)
	@PostMapping
	public ResponseEntity<MilestoneResponse> addMilestone(@RequestBody MilestoneCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(milestoneService.addMilestone(request));
	}

	@ApiOperation(
		value = "Milestone 전체 조회",
		notes = "전체 Milestone 목록을 조회한다.",
		produces = "application/json"
	)
	@GetMapping
	public ResponseEntity<List<MilestoneResponse>> findAll() {
		return ResponseEntity.ok(milestoneService.findAll());
	}

	@ApiOperation(
		value = "Milestone 수정",
		notes = "Milestone을 수정한다",
		produces = "application/json"
	)
	@PatchMapping("/{id}")
	public ResponseEntity<MilestoneResponse> updateMilestone(@PathVariable Long id,
		@RequestBody MilestoneModifyRequest request) {
		return ResponseEntity.ok(milestoneService.modifyMilestone(id, request));
	}

	@ApiOperation(
		value = "Milestone 일괄 삭제",
		notes = "입력된 id에 해당되는 Milestone들을 일괄 삭제한다.",
		produces = "application/json"
	)
	@DeleteMapping
	public ResponseEntity<List<Long>> deleteById(@RequestParam("id") List<Long> ids) {
		return ResponseEntity.ok(milestoneService.deleteById(ids));
	}

}
