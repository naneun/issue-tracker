package com.team03.issuetracker.issue.domain;

import static com.team03.issuetracker.issue.domain.IssueState.OPEN;
import static com.team03.issuetracker.issue.domain.IssueState.nextState;

import com.team03.issuetracker.common.domain.BaseTimeEntity;
import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.milestone.domain.Milestone;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;
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

	@OneToMany(mappedBy = "issue", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	//	@OnDelete(action = OnDeleteAction.CASCADE)
	private final List<Comment> comments = new ArrayList<>();

	/********************************************************************/

	@Builder
	/* 클래스에 하면 모든 필드에 대한 빌더메서드가 만들어지지만,
	메서드나 생성자에 붙이면 인자들에 대해서만 빌더 메서드가 만들어진다.
	즉, 여기서는 인자에 없는 state, creator, modifier가 빠지게 된다 */
	private Issue(Long id, String title, String content, Label label, Milestone milestone,
		Member assignee, List<Comment> comments) {

		this.id = id;
		this.title = title;
		this.content = content;
		this.state = OPEN; /* state이 여기는 있지만 인자에는 없어서 빌더 메서드가 안 생긴다 */
		this.label = label;
		this.milestone = milestone;
		this.assignee = assignee;
		if (Objects.nonNull(comments)) {
			this.comments.addAll(comments);
		}
	}

	public static Issue of(Long id, String title, String content, Label label, Milestone milestone,
		Member assignee, List<Comment> comments) {
		/* 기본 정적 팩토리 메서드에서는 여기에서 new를 이용해 생성자를 호출하여 반환한다 (아래 주석처리 부분)*/
		/* 하지만 이때 생성자에서 인자의 순서가 바뀐다면(ex:같은 String 타입인 content<->title) 컴파일 에러로 잡아내지 못하는 문제가 있다 */
		/* 그래서 builder를 사용한다. 생성자 시그니처에서 content, title 위치가 바뀌어도 content는 content()에, title은 title()에 들어간다 */
		//		return new Issue(id,title, content, label, milestone, assignee, comments);
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

	public void truncateComments() {
		this.comments.forEach(comment -> comment.fixIssue(null));
		this.comments.clear();
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
