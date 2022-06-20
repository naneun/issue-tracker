package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.common.domain.dto.LoginMemberResponse;
import com.team03.issuetracker.common.domain.repository.MemberRepository;
import com.team03.issuetracker.oauth.common.ResourceServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final MemberRepository memberRepository;

	@Transactional
	public LoginMemberResponse login(Member oauthMember) {
		String serialNumber = oauthMember.getSerialNumber();
		ResourceServer resourceServer = oauthMember.getResourceServer();

		Member loginMember = memberRepository.findBySerialNumberAndResourceServer(serialNumber, resourceServer)
			.map(member -> member.updateLoginInfo(oauthMember))
			.orElseGet(() -> memberRepository.save(oauthMember));

		return new LoginMemberResponse(loginMember);
	}
}
