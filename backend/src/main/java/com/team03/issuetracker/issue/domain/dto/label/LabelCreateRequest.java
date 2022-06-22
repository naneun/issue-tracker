package com.team03.issuetracker.issue.domain.dto.label;

import com.team03.issuetracker.issue.domain.Label;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LabelCreateRequest {

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String backgroundColor;

    public Label toEntity() {
        return Label.builder()
            .title(title)
            .description(description)
            .backgroundColor(backgroundColor)
            .build();
    }
}
