package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.common.component.LoginMember;
import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.common.exception.MemberException;
import com.team03.issuetracker.common.repository.MemberRepository;
import com.team03.issuetracker.oauth.common.ResourceServer;
import com.team03.issuetracker.oauth.dto.OAuthUser;
import com.team03.issuetracker.oauth.exception.LoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

	private final MemberRepository memberRepository;
	private final LoginMember loginMember;

	@Transactional
	public Member save(OAuthUser oAuthUser) {
		String serialNumber = oAuthUser.getSerialNumber();
		ResourceServer resourceServer = oAuthUser.getResourceServer();

		Member loginMember = oAuthUser.toEntity();

		return memberRepository.findBySerialNumberAndResourceServer(serialNumber, resourceServer)
			.map(member -> member.updateLoginInfo(loginMember))
			.orElseGet(() -> memberRepository.save(loginMember));
	}

	@Transactional(readOnly = true)
	public void updateLoginMemberById(Long id) {
		Member member = memberRepository.findById(id)
			.orElseThrow(MemberException::new);

		/* member를 update 치는게 아니고 Bean으로 등록된 LoginMember 필드를 현재 member의 정보로 채움*/
		/* loginMember의 메서드가 실행될 때 실제 빈이 호출된다(@Scope의 proxyMode 설정에 따라)*/
		/* update 호출 전까지는 LoginMember$$EnhancerBySpringCGLIB$$c393efa6 와 같은 프록시 객체가 출력된다 */
		log.info("LoginService loginMember : {}", loginMember.getClass());
		loginMember.update(member);
	}

	@Transactional(readOnly = true)
	public Member findById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(LoginException::new);
	}
}
