package com.team03.issuetracker.issue.api;

import com.team03.issuetracker.issue.application.IssueService;
import com.team03.issuetracker.issue.domain.IssueState;
import com.team03.issuetracker.issue.domain.dto.issue.IssueAddRequest;
import com.team03.issuetracker.issue.domain.dto.issue.IssueModifyRequest;
import com.team03.issuetracker.issue.domain.dto.issue.IssueResponse;
import com.team03.issuetracker.issue.domain.dto.issue.IssueSimpleResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/issues")
    public ResponseEntity<List<IssueSimpleResponse>> findByState(IssueState state) {
        return ResponseEntity.ok(issueService.findByState(state));
    }

    @PostMapping("/issues")
    public ResponseEntity<IssueResponse> addIssue(IssueAddRequest issueAddRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(issueService.addIssue(issueAddRequest));
    }

    @PatchMapping("issues")
    public ResponseEntity<IssueResponse> modifyIssue(IssueModifyRequest issueModifyRequest) {
        return ResponseEntity.ok().body(issueService.modifyIssue(issueModifyRequest));
    }

    @PatchMapping("issues/state")
    public ResponseEntity<List<IssueResponse>> modifyIssue(List<Long> checkedIds) {
        return ResponseEntity.ok().body(issueService.changeStateById(checkedIds));
    }
}

//http://localhost:8080/issues?title=title&content=content&labelId=1&milestoneId=1&assigneeId=1
