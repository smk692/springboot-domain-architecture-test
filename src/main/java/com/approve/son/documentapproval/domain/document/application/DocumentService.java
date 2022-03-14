package com.approve.son.documentapproval.domain.document.application;

import com.approve.son.documentapproval.domain.document.domain.Document;
import com.approve.son.documentapproval.domain.document.dto.DocumentDto;
import com.approve.son.documentapproval.domain.document.dto.UpsertDocumentDto;
import com.approve.son.documentapproval.domain.document.repository.DocumentRepository;
import com.approve.son.documentapproval.global.entity.EnumType;
import com.approve.son.documentapproval.global.system.constants.ErrorCode;
import com.approve.son.documentapproval.global.system.exception.handler.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    /**
     * @name : createDocument
     * @param : UpsertDocumentDto
     * @return : Long
     * @description : 문서 생성 (문서 생성 시 저장 후 상신 할 수 있다.)
     */
    @Transactional
    public Long createDocument(UpsertDocumentDto dto) {
        return documentRepository.save(
                new Document(
                        dto.getTitle(),
                        dto.getDocumentType(),
                        dto.getContents(),
                        dto.getDocumentApprovalGroupDto()
                )).getId();
    }

    /**
     * @name : getDocument
     * @param : id
     * @return : DocumentDto
     * @description : 문서 단 건 조회
     */
    public DocumentDto getDocumentById(Long id) {
        final Document documentEntity = documentRepository.findById(id).orElseThrow(
                () -> new BadRequestException(ErrorCode.NOT_FIND_DATA)
        );
        return documentEntity.toDto();
    }

    /**
     * @name : getAllDocument
     * @param : id
     * @return : DocumentDto
     * @description : 문서 전체 조회
     */
    public List<DocumentDto> getDocumentList(EnumType.DocumentSearchType documentSearchType) {
        final List<DocumentDto> documentEntity = documentRepository.selectDocumentList(documentSearchType);

        return documentEntity;
    }

    /**
     * @name : createDocument
     * @param : UpsertDocumentDto
     * @return : Long
     * @description : 문서 수정
     */
    @Transactional
    public Long updateDocument(UpsertDocumentDto dto) {
        final Document entity = documentRepository.findById(dto.getId()).orElseThrow(
                () -> new BadRequestException(ErrorCode.NOT_FIND_DATA)
        );

        // 문서 내용 수정
        entity.documentUpdate(dto);

        return entity.getId();
    }

    /**
     * @name : approvalDocument
     * @param : UpsertDocumentDto
     * @return : Long
     * @description : 문서 결재 상신
     */
    @Transactional
    public Long approvalDocument(UpsertDocumentDto dto) {
        final Document entity = documentRepository.findById(dto.getId()).orElseThrow(
                () -> new BadRequestException(ErrorCode.NOT_FIND_DATA)
        );
        // 문서 결재 상신
        entity.documentApproval(dto);

        return entity.getId();
    }


    /**
     * @name : approvalGroupReject
     * @param : UpdateDocumentApprovalGroupDto
     * @return : Long
     * @description : 문서 결재 그룹 결재자 거절
     */
    @Transactional
    public Long approvalGroupReject(Long id) {
        final Document entity = documentRepository.findById(id).orElseThrow(
                () -> new BadRequestException(ErrorCode.NOT_FIND_DATA)
        );
        // 결재자 결재 반려
        entity.approvalGroupReject();

        return entity.getId();
    }

    /**
     * @name : approveGroupConfirm
     * @param : UpdateDocumentApprovalGroupDto
     * @return : Long
     * @description : 문서 결재 그룹 결재자 승인
     */
    @Transactional
    public Long approveGroupConfirm(Long id) {
        final Document documentEntity = documentRepository.findById(id).orElseThrow(
                () -> new BadRequestException(ErrorCode.NOT_FIND_DATA)
        );
        // 결재자 결재 승인
        documentEntity.approveGroupConfirm();

        return documentEntity.getId();
    }

}
