package com.team03.issuetracker.issue.domain;

import com.team03.issuetracker.common.domain.BaseTimeEntity;
import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.milestone.domain.Milestone;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static com.team03.issuetracker.issue.domain.IssueState.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Issue extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Enumerated(EnumType.STRING)
    private IssueState state;

    @ManyToOne
    @JoinColumn
    private Label label;

    @ManyToOne
    @JoinColumn
    private Milestone milestone;

    @ManyToOne
    @JoinColumn
    private Member assignee;

    @CreatedBy
    @ManyToOne
    @JoinColumn(updatable = false)
    private Member creator;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn
    private Member modifier;

    @Builder
    private Issue(Long id, String title, String content, Label label, Milestone milestone, Member assignee) {
        this.title = title;
        this.content = content;
        this.state = OPEN;
        this.label = label;
        this.milestone = milestone;
        this.assignee = assignee;
    }

    public static Issue of(Long id, String title, String content, Label label, Milestone milestone, Member assignee) {
        return Issue.builder()
                .title(title)
                .content(content)
                .label(label)
                .milestone(milestone)
                .assignee(assignee)
                .build();
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeState() {
        this.state = IssueState.values()[this.state.ordinal() + 1 % values().length];
    }

    public void changeLabel(Label label) {
        this.label = label;
    }

    public void changeMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public void changeAssignee(Member assignee) {
        this.assignee = assignee;
    }
}
