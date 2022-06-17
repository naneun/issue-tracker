package com.team03.issuetracker.common.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Builder
    private Member(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static Member of(Long id, String email) {
        return Member.builder()
                .id(id)
                .email(email)
                .build();
    }
}
