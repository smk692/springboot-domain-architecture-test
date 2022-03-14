package com.approve.son.documentapproval.domain.document.dto;

import com.approve.son.documentapproval.global.entity.EnumType.DocumentStatus;
import com.approve.son.documentapproval.global.entity.EnumType.DocumentType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DocumentDto {
    private Long id;
    // 제목
    private String title;
    // enum('휴가신청서', '지출결의서')
    private DocumentType documentType;
    // 문서 내용
    private String contents;
    // 문서 상태 (enum 저장, 결재 진행중, 상신 반려, 결재 완료)
    private DocumentStatus documentStatus;
    // 문서 결재 관련 객체
    private DocumentApprovalDto documentApproval;
    // 문서 결재자 그룹 데이터
    private List<DocumentApprovalGroupDto> documentApprovalGroupDto;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdMemberId;
    private Long updatedMemberId;

    @Builder
    public DocumentDto(Long id, String title, DocumentType documentType, String contents, DocumentStatus documentStatus, DocumentApprovalDto documentApproval,List<DocumentApprovalGroupDto> documentApprovalGroupDto) {
        this.id = id;
        this.title = title;
        this.documentType = documentType;
        this.contents = contents;
        this.documentStatus = documentStatus;
        this.documentApproval = documentApproval;
        this.documentApprovalGroupDto = documentApprovalGroupDto;
    }

    @Builder
    public DocumentDto(Long id, String title, DocumentType documentType, String contents, DocumentStatus documentStatus, DocumentApprovalDto documentApproval, List<DocumentApprovalGroupDto> documentApprovalGroupDto, LocalDateTime createdAt, LocalDateTime updatedAt, Long createdMemberId, Long updatedMemberId) {
        this.id = id;
        this.title = title;
        this.documentType = documentType;
        this.contents = contents;
        this.documentStatus = documentStatus;
        this.documentApproval = documentApproval;
        this.documentApprovalGroupDto = documentApprovalGroupDto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdMemberId = createdMemberId;
        this.updatedMemberId = updatedMemberId;
    }

    public DocumentDto(Long id, String title, DocumentType documentType, String contents, DocumentStatus documentStatus, DocumentApprovalDto documentApproval, LocalDateTime createdAt, LocalDateTime updatedAt, Long createdMemberId, Long updatedMemberId) {
        this.id = id;
        this.title = title;
        this.documentType = documentType;
        this.contents = contents;
        this.documentStatus = documentStatus;
        this.documentApproval = documentApproval;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdMemberId = createdMemberId;
        this.updatedMemberId = updatedMemberId;
    }
}
