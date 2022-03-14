package com.approve.son.documentapproval.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDto {

    private Long id;
    private String name;
    private String email;
    private String loginId;
    private String loginPassword;

    @Builder
    public MemberDto(Long id, String name, String email, String loginId, String loginPassword) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.loginId = loginId;
        this.loginPassword = loginPassword;
    }
}
