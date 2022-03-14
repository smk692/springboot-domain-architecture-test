package com.approve.son.documentapproval.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class LoginDto {
    @NotNull
    private String loginId;
    @NotNull
    private String loginPassword;

    @Builder
    public LoginDto(String loginId, String loginPassword) {
        this.loginId = loginId;
        this.loginPassword = loginPassword;
    }
}
