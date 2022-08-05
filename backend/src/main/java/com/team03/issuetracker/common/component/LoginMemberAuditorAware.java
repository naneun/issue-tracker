package com.team03.issuetracker.common.component;

import com.team03.issuetracker.common.domain.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginMemberAuditorAware implements AuditorAware<Member> {

	private final LoginMember loginMember;

	@Override
	public Optional<Member> getCurrentAuditor() {
		log.info("\n===================== LoginMemberAuditor 발동!");
		return Optional.of(loginMember.toEntity());
	}
}
