package com.team03.issuetracker.issue.domain;

import com.team03.issuetracker.common.domain.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString(exclude = "writer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    @ManyToOne
    @JoinColumn(updatable = false)
    private Member writer;

    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

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
}
