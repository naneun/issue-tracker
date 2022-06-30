package com.team03.issuetracker.issue.domain.dto.issue.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IssueSearchText {

    @JsonProperty
    String text;
}
