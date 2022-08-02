package com.team03.issuetracker.common.component;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.common.ResourceServer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
//@ToString
@NoArgsConstructor
@Slf4j
public class LoginMember {

	private Long id;
	private String serialNumber;
	private ResourceServer resourceServer;
	private String name;
	private String email;
	private String profileImage;
	private String oAuthAccessToken;

	public void update(Member member) {
		this.id = member.getId();
		this.serialNumber = member.getSerialNumber();
		this.resourceServer = member.getResourceServer();
		this.name = member.getName();
		this.email = member.getEmail();
		this.profileImage = member.getProfileImage();
		this.oAuthAccessToken = member.getOAuthAccessToken();
		log.info("@@@@@@@@@@@@@@@@ LoginMember 업데이트");
		log.info("{}", this);
	}

	public Member toEntity() {
		return Member.builder()
			.id(id)
			.serialNumber(serialNumber)
			.resourceServer(resourceServer)
			.name(name)
			.email(email)
			.profileImage(profileImage)
			.oAuthAccessToken(oAuthAccessToken)
			.build();
	}
}
