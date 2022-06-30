package com.team03.issuetracker.common.api;

import com.team03.issuetracker.common.application.MemberService;
import com.team03.issuetracker.common.domain.dto.MemberResponse;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;

	@ApiOperation(
		value = "담당자로 등록할 사용자 목록 출력",
		notes = "담당자로 등록할 사용자 목록을 출력한다.",
		produces = "application/json",
		response = MemberResponse.class
	)
	@GetMapping
	public ResponseEntity<List<MemberResponse>> upload() {
		return ResponseEntity.ok(memberService.findAll());
	}
}
