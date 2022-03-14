package com.approve.son.documentapproval.domain.document.dto;
import com.approve.son.documentapproval.global.entity.EnumType.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDocumentApprovalGroupDto {
    private Long id;
    private Long documentId;
    private DocumentApprovalGroupStatus documentApprovalGroupStatus;
    private Long approvalMemberId;
    private String comment;
    private Long sort;

    @Builder
    public UpdateDocumentApprovalGroupDto(Long id, Long documentId, DocumentApprovalGroupStatus documentApprovalGroupStatus, Long approvalMemberId, String comment, Long sort) {
        this.id = id;
        this.documentId = documentId;
        this.documentApprovalGroupStatus = documentApprovalGroupStatus;
        this.approvalMemberId = approvalMemberId;
        this.comment = comment;
        this.sort = sort;
    }
}
