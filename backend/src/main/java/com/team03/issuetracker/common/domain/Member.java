package com.team03.issuetracker.common.domain;

import com.team03.issuetracker.oauth.common.ResourceServer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String serialNumber;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private ResourceServer resourceServer;

    private String name;
    private String email;
    private String profileImage;
    private String oAuthAccessToken;

    @Builder
    private Member(Long id, String serialNumber, ResourceServer resourceServer, String name,
        String email, String profileImage, String oAuthAccessToken) {

        this.id = id;
        this.serialNumber = serialNumber;
        this.resourceServer = resourceServer;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.oAuthAccessToken = oAuthAccessToken;
    }

    public static Member of(Long id, String email, String name) {
        return Member.builder()
            .id(id)
            .email(email)
            .name(name)
            .build();
    }

    public Member updateLoginInfo(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.oAuthAccessToken = member.getOAuthAccessToken();
        this.profileImage = member.getProfileImage();
        return this;
    }
}
