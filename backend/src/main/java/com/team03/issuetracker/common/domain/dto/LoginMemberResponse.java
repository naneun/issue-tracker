package com.team03.issuetracker.common.domain.dto;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.common.ResourceServer;
import lombok.Getter;

@Getter
public class LoginMemberResponse {

	private final Long id;
	private final String name;
	private final String serialNumber;
	private final ResourceServer resourceServer;

	public LoginMemberResponse(Member member) {
		this.id = member.getId();
		this.name = member.getName();
		this.serialNumber = member.getSerialNumber();
		this.resourceServer = member.getResourceServer();
	}
}
