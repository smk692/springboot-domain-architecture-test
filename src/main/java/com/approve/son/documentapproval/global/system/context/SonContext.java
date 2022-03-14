package com.approve.son.documentapproval.global.system.context;

import com.approve.son.documentapproval.domain.member.dto.MemberDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SonContext {
    private Long currentMemberId;
    private MemberDto member;
}
