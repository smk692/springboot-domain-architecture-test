package com.approve.son.documentapproval.domain.document.domain;

import com.approve.son.documentapproval.global.entity.EnumType;
import com.approve.son.documentapproval.global.system.context.SonContextHolder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentApproval {

    // 상신자 ID
    @Column(name = "approval_send_member_id")
    private Long approvalSendMemberId;

    // 상신일
    @Column(name = "approval_send_date")
    private LocalDateTime approvalSendDate;

    // 상신 완료일
    @Column(name = "approval_complete_date")
    private LocalDateTime approvalCompleteDate;

    // 상신 반려일
    @Column(name = "approval_reject_date")
    private LocalDateTime approvalRejectDate;

    public DocumentApproval(EnumType.DocumentStatus documentStatus) {
        if (documentStatus.equals(EnumType.DocumentStatus.PROGRESS)) {
            this.approvalSendMemberId = SonContextHolder.getContext().getCurrentMemberId();
            this.approvalSendDate = LocalDateTime.now();
        }
    }

    // 결재자 승인
    public void documentApprovalUpdate() {
        this.approvalCompleteDate = LocalDateTime.now();
    }

    // 결재자 반려
    public void documentRejectUpdate() {
        this.approvalRejectDate = LocalDateTime.now();
    }
}
