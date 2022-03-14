package com.approve.son.documentapproval.domain.document.dto;

import com.approve.son.documentapproval.global.entity.EnumType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpsertDocumentDto {
    private Long id;
    // 제목
    private String title;
    // enum('휴가신청서', '지출결의서')
    private EnumType.DocumentType documentType;
    // 문서 내용
    private String contents;
    // 문서 결재 관련 객체
    private DocumentApprovalDto documentApproval;
    // 결재 그룹
    private List<DocumentApprovalGroupDto> documentApprovalGroupDto = new ArrayList<>();

    @Builder
    public UpsertDocumentDto(String title, EnumType.DocumentType documentType, String contents, DocumentApprovalDto documentApproval, List<DocumentApprovalGroupDto> documentApprovalGroupDto) {
        this.title = title;
        this.documentType = documentType;
        this.contents = contents;
        this.documentApproval = documentApproval;
        this.documentApprovalGroupDto = documentApprovalGroupDto;
    }

    @Builder
    public UpsertDocumentDto(Long id, String title, EnumType.DocumentType documentType, String contents, DocumentApprovalDto documentApproval, List<DocumentApprovalGroupDto> documentApprovalGroupDto) {
        this.id = id;
        this.title = title;
        this.documentType = documentType;
        this.contents = contents;
        this.documentApproval = documentApproval;
        this.documentApprovalGroupDto = documentApprovalGroupDto;
    }
}
