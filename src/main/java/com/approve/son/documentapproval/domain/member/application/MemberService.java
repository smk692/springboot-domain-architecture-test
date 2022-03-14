package com.approve.son.documentapproval.domain.member.application;

import com.approve.son.documentapproval.domain.member.domain.Member;
import com.approve.son.documentapproval.domain.member.dto.MemberDto;
import com.approve.son.documentapproval.domain.member.exception.UsernameNotFoundException;
import com.approve.son.documentapproval.domain.member.repository.MemberRepository;
import com.approve.son.documentapproval.global.system.constants.ErrorCode;
import com.approve.son.documentapproval.global.util.CryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CryptUtil cryptUtil;

    /**
     * @name : getMemberLogin
     * @param :
     * @return : MemberDto
     * @description : 로그인
     */
    public MemberDto getMemberLogin(String loginId, String loginPassword) {
        final Optional<Member> member = memberRepository.findByLoginId(loginId);

        if (!member.isPresent()) {
            throw new UsernameNotFoundException(ErrorCode.NOT_FIND_USER_ID);
        }

        final Optional<Member> memberInfo = member.stream()
                .parallel()
                .filter(e-> cryptUtil.matches(loginPassword, e.getLoginPassword()))
                .findFirst();

        if (!memberInfo.isPresent()) {
            throw new UsernameNotFoundException(ErrorCode.NOT_FIND_USER_PASSWORD);
        }

        return memberInfo.get().toDto();
    }

    /**
     * @name : getMemberList
     * @param :
     * @return : ResponseEntity
     * @description : 문서 목록 조회
     */
    public List<MemberDto> getMemberList() {
        final List<Member> list = memberRepository.findAll();

        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        return list.stream().map(Member::toDto).collect(Collectors.toList());
    }
}
