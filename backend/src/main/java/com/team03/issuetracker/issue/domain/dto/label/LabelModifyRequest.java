package com.team03.issuetracker.issue.domain.dto.label;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LabelModifyRequest {

    @NotBlank
    private String title;
    private String description;
    private String backgroundColor;
}
