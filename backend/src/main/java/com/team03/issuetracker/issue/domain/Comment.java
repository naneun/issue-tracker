package com.team03.issuetracker.issue.domain;

import com.team03.issuetracker.common.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    @JoinColumn(updatable = false)
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

    public void changeContent(String content) {
        this.content = content;
    }

    public void addEmoji(Emoji emoji) {
        this.emoji = emoji;
    }
}
