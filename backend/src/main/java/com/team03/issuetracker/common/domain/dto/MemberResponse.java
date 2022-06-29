package com.team03.issuetracker.common.domain.dto;

import com.team03.issuetracker.common.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponse {

	private Long memberId;
	private String name;
	private String profileImage;

	public static MemberResponse from(Member member) {
		return new MemberResponse(
			member.getId(),
			member.getName(),
			member.getProfileImage()
		);
	}
}
