package com.approve.son.documentapproval.domain.document.dto;

import com.approve.son.documentapproval.global.entity.EnumType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentApprovalGroupDto {
    private Long id;
    private EnumType.DocumentApprovalGroupStatus documentApprovalGroupStatus;
    private Long approvalMemberId;
    private String comment;
    private Long sort;

    @Builder
    public DocumentApprovalGroupDto(Long id, EnumType.DocumentApprovalGroupStatus documentApprovalGroupStatus, Long approvalMemberId, String comment, Long sort) {
        this.id = id;
        this.documentApprovalGroupStatus = documentApprovalGroupStatus;
        this.approvalMemberId = approvalMemberId;
        this.comment = comment;
        this.sort = sort;
    }
}
