package com.approve.son.documentapproval.domain.document.domain;

import com.approve.son.documentapproval.domain.document.dto.DocumentApprovalGroupDto;
import com.approve.son.documentapproval.global.entity.BaseEntity;
import com.approve.son.documentapproval.global.entity.EnumType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "document_approval_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DocumentApprovalGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "document_id")
    private Long documentId;

    // enum (미확인, 승인, 거절)
    @Column(name = "Document_approval_group_status")
    @Enumerated(STRING)
    private EnumType.DocumentApprovalGroupStatus documentApprovalGroupStatus;

    @Column(name = "approval_member_id")
    private Long approvalMemberId;

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @Column(name = "sort", nullable = false)
    private Long sort;

    public DocumentApprovalGroup(Long approvalMemberId, String comment, Long sort) {
        this.documentApprovalGroupStatus = EnumType.DocumentApprovalGroupStatus.UNCONFIRMED;
        this.approvalMemberId = approvalMemberId;
        this.comment = comment;
        this.sort = sort;
    }

    // Entity -> Dto
    public DocumentApprovalGroupDto toDto() {
        return new DocumentApprovalGroupDto(this.id, this.documentApprovalGroupStatus, this.approvalMemberId, this.comment, this.sort);
    }

    // 결재자 승인
    public void approvalUpdate() {
        this.documentApprovalGroupStatus = EnumType.DocumentApprovalGroupStatus.APPROVAL;
    }

    // 결재자 반려
    public void rejectUpdate() {
        this.documentApprovalGroupStatus = EnumType.DocumentApprovalGroupStatus.REJECT;
    }
}

