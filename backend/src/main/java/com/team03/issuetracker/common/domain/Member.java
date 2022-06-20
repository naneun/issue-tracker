package com.team03.issuetracker.common.domain;

import com.team03.issuetracker.oauth.common.ResourceServer;
import javax.persistence.*;
import lombok.*;

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

	private String oauthAccessToken;

	private String oauthRefreshToken;

	@Builder
	private Member(Long id, String serialNumber, ResourceServer resourceServer, String name, String email,
		String profileImage, String oauthAccessToken, String oauthRefreshToken) {
		this.id = id;
		this.serialNumber = serialNumber;
		this.resourceServer = resourceServer;
		this.name = name;
		this.email = email;
		this.profileImage = profileImage;
		this.oauthAccessToken = oauthAccessToken;
		this.oauthRefreshToken = oauthRefreshToken;
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
		this.oauthAccessToken = member.getOauthAccessToken();
		this.oauthRefreshToken = member.getOauthRefreshToken();
		this.profileImage = member.getProfileImage();
		return null;
	}
}
