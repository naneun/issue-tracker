package com.team03.issuetracker.common.component;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.application.LoginService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginMemberAuditorAware implements AuditorAware<Member> {

    private final LoginMember loginMember;
    private final LoginService loginService;

    @Override
    public Optional<Member> getCurrentAuditor() {
        loginService.updateLoginMemberById(3L);
        return Optional.of(loginMember.toEntity());
    }
}
