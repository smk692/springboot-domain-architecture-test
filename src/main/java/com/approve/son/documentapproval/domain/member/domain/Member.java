package com.approve.son.documentapproval.domain.member.domain;


import com.approve.son.documentapproval.domain.member.dto.MemberDto;
import com.approve.son.documentapproval.global.entity.BaseEntity;
import com.approve.son.documentapproval.global.system.constants.ErrorCode;
import com.approve.son.documentapproval.global.system.exception.handler.BadRequestException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Column(name = "login_id", length = 50)
    private String loginId;
    @Column(name = "login_password", length = 500)
    private String loginPassword;

    @Builder
    public Member(String name, String email, String loginId, String loginPassword) {
        this.validate();

        this.name = name;
        this.email = email;
        this.loginId = loginId;
        this.loginPassword = loginPassword;
    }

    public MemberDto toDto() {
        return MemberDto.builder()
                .id(this.getId())
                .name(this.getName())
                .email(this.getEmail())
                .loginId(this.getLoginId())
                .build();
    }

    public void validate() {
        if (getEmailHost().isBlank() || getEmailId().isEmpty()) {
            throw new BadRequestException(ErrorCode.INVALID_INPUT_EMAIL);
        }
    }
    public String getEmailHost() {
        final int index = this.email.indexOf("@");
        return index == -1 ? null : this.email.substring(index + 1);
    }

    public String getEmailId() {
        final int index = email.indexOf("@");
        return index == -1 ? null : this.email.substring(0, index);
    }
}
