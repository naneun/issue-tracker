package com.team03.issuetracker.issue.domain;

import static com.team03.issuetracker.issue.domain.IssueState.OPEN;
import static com.team03.issuetracker.issue.domain.IssueState.nextState;

import com.team03.issuetracker.common.domain.BaseTimeEntity;
import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.milestone.domain.Milestone;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

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

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final List<Comment> comments = new ArrayList<>();

    /********************************************************************/

    @Builder
    private Issue(Long id, String title, String content, Label label, Milestone milestone,
        Member assignee, List<Comment> comments) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.state = OPEN;
        this.label = label;
        this.milestone = milestone;
        this.assignee = assignee;
        if (Objects.nonNull(comments)) {
            this.comments.addAll(comments);
        }
    }

    public static Issue of(Long id, String title, String content, Label label, Milestone milestone,
        Member assignee, List<Comment> comments) {
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

    public void setState(IssueState state) {
        this.state = state;
    }

    public void changeLabel(Label label) {
        this.label = label;
    }

    public void changeMilestone(Milestone milestone) {
        if (this.milestone != null && this.milestone.hasIssue(this)) {
            this.milestone.removeIssue(this);
        }
        if (milestone != null) {
            milestone.addIssue(this);
        }
        this.milestone = milestone;
    }

    public void changeAssignee(Member assignee) {
        this.assignee = assignee;
    }

    public void appendComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    /********************************************************************/

    public Issue merge(Issue updated) {
        if (Strings.isNotBlank(updated.title)) {
            title = updated.title;
        }
        if (Strings.isNotBlank(updated.content)) {
            content = updated.content;
        }
        if (Objects.nonNull(updated.label)) {
            label = updated.label;
        }
        if (Objects.nonNull(updated.milestone)) {
            milestone = updated.milestone;
        }
        if (Objects.nonNull(updated.assignee)) {
            assignee = updated.assignee;
        }
        return this;
    }
}
