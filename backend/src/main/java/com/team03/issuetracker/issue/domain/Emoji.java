package com.team03.issuetracker.issue.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Emoji {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String unicode;

    private String description;

    @Builder
    private Emoji(Long id, String unicode, String description) {
        this.id = id;
        this.unicode = unicode;
        this.description = description;
    }

    public static Emoji of(Long id, String unicode, String description) {
        return Emoji.builder()
                .id(id)
                .unicode(unicode)
                .description(description)
                .build();
    }
}
