package com.team03.issuetracker.common.application;

import com.team03.issuetracker.common.domain.dto.MemberResponse;
import com.team03.issuetracker.common.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public List<MemberResponse> findAll() {

		return memberRepository.findAll().stream()
			.map(MemberResponse::from)
			.collect(Collectors.toList());
	}
}
