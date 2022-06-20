package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.common.domain.MemberRepository;
import com.team03.issuetracker.oauth.common.ResourceServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

	private final MemberRepository memberRepository;

	public void login(Member oauthMember) {
		String serialNumber = oauthMember.getSerialNumber();
		ResourceServer resourceServer = oauthMember.getResourceServer();

		memberRepository.findBySerialNumberAndResourceServer(serialNumber, resourceServer)
			.ifPresentOrElse(
				member -> member.updateLoginInfo(oauthMember),
				() -> memberRepository.save(oauthMember)
			);
	}
}
