package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.common.component.LoginMember;
import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.common.exception.MemberException;
import com.team03.issuetracker.common.repository.MemberRepository;
import com.team03.issuetracker.oauth.common.ResourceServer;
import com.team03.issuetracker.oauth.dto.OAuthUser;
import com.team03.issuetracker.oauth.exception.LoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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

        loginMember.update(member);
    }

    @Transactional(readOnly = true)
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(LoginException::new);
    }
}
