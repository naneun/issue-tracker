package com.team03.issuetracker.issue.domain;

import com.team03.issuetracker.common.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedBy
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	@ToString.Exclude
	private Member writer;

	@Lob
	private String content;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	@ToString.Exclude
	private Emoji emoji;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn()
	@ToString.Exclude
	private Issue issue;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdDate;

	/********************************************************************/

	@Builder
	private Comment(Long id, Member writer, String content) {
		this.id = id;
		this.writer = writer;
		this.content = content;
	}

	public static Comment of(Long id, Member writer, String content) {
		return Comment.builder()
			.id(id)
			.writer(writer)
			.content(content)
			.build();
	}

	/********************************************************************/

	public void fixIssue(Issue issue) {
		this.issue = issue;
	}

	public void changeContent(String content) {
		this.content = content;
	}

	public void addEmoji(Emoji emoji) {
		this.emoji = emoji;
	}

	/********************************************************************/

	public Comment merge(Comment updated) {
		if (Strings.isNotBlank(updated.content)) {
			content = updated.content;
		}
		return this;
	}
}
