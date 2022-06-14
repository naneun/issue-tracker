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

import static com.team03.issuetracker.issue.domain.IssueState.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Issue extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

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

    public void close() {
        state = CLOSE;
    }
}
