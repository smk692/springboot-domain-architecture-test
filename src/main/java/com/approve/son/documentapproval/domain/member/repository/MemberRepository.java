package com.approve.son.documentapproval.domain.member.repository;

import com.approve.son.documentapproval.domain.member.domain.Member;
import com.approve.son.documentapproval.domain.member.repository.custom.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByLoginId(String loginId);
}
