package com.team03.issuetracker.issue.domain;

import com.team03.issuetracker.common.domain.BaseTimeEntity;
import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.milestone.domain.Milestone;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

import static com.team03.issuetracker.issue.domain.IssueState.*;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Issue extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column
    private String title;

    @NotBlank
    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    private IssueState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Label label;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @ToString.Exclude
    private Milestone milestone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member assignee;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private Member creator;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member modifier;

    @OneToMany(mappedBy = "issue", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private final List<Comment> comments = new ArrayList<>();

    /********************************************************************/

    @Builder
    private Issue(Long id, String title, String content, Label label, Milestone milestone, Member assignee, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.state = OPEN;
        this.label = label;
        this.milestone = milestone;
        this.assignee = assignee;
        this.comments.addAll(comments);
    }

    public static Issue of(Long id, String title, String content, Label label, Milestone milestone, Member assignee, List<Comment> comments) {
        return Issue.builder()
                .id(id)
                .title(title)
                .content(content)
                .label(label)
                .milestone(milestone)
                .assignee(assignee)
                .comments(comments)
                .build();
    }

    /********************************************************************/

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeState() {
        this.state = nextState(this.state);
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
