package com.approve.son.documentapproval.domain.document.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DocumentApprovalDto {
    // 상신자
    private Long approvalSendMemberId;
    // 상신일
    private LocalDateTime approvalSendDate;
    // 결재 완료일
    private LocalDateTime approvalCompleteDate;
    // 상신 반려일
    private LocalDateTime approvalRejectDate;

    @Builder
    public DocumentApprovalDto(Long approvalSendMemberId, LocalDateTime approvalSendDate, LocalDateTime approvalCompleteDate, LocalDateTime approvalRejectDate) {
        this.approvalSendMemberId = approvalSendMemberId;
        this.approvalSendDate = approvalSendDate;
        this.approvalCompleteDate = approvalCompleteDate;
        this.approvalRejectDate = approvalRejectDate;
    }
}
