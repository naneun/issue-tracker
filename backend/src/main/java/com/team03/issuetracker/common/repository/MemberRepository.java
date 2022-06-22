package com.team03.issuetracker.common.repository;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.oauth.common.ResourceServer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySerialNumberAndResourceServer(String serialNumber, ResourceServer resourceServer);
}
