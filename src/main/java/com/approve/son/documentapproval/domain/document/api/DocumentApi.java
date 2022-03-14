package com.approve.son.documentapproval.domain.document.api;

import com.approve.son.documentapproval.domain.document.application.DocumentService;
import com.approve.son.documentapproval.domain.document.dto.DocumentDto;
import com.approve.son.documentapproval.domain.document.dto.UpsertDocumentDto;
import com.approve.son.documentapproval.global.entity.EnumType;
import com.approve.son.documentapproval.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/document")
public class DocumentApi {

    private final DocumentService documentService;

    /**
     * @name : createDocument
     * @param : UpsertDocumentDto
     * @return : Long
     * @description : 문서 생성 (생성 시 저장 후 상신 할 수 있다.)
     */
    @PostMapping("/create")
    public ResponseEntity<?> createDocument(@RequestBody UpsertDocumentDto dto) {
        Long result = documentService.createDocument(dto);

        return ResponseEntity.ok().body(new CommonResponse(result));
    }

    /**
     * @name : getDocumentById
     * @param : id
     * @return : Long
     * @description : 문서 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getDocumentById(@PathVariable Long id) {
        DocumentDto result = documentService.getDocumentById(id);

        return ResponseEntity.ok().body(new CommonResponse(result));
    }

    /**
     * @name : getDocumentById
     * @param : id
     * @return : Long
     * @description : 문서 목록 조회
     */
    @GetMapping("")
    public ResponseEntity<?> getDocumentList(@RequestParam EnumType.DocumentSearchType documentSearchType) {
        List<DocumentDto> result = documentService.getDocumentList(documentSearchType);

        return ResponseEntity.ok().body(new CommonResponse(result));
    }

    /**
     * @name : updateDocument
     * @param : UpsertDocumentDto
     * @return : Long
     * @description : 문서 저장
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateDocument(@RequestBody UpsertDocumentDto dto) {
        Long result = documentService.updateDocument(dto);

        return ResponseEntity.ok().body(new CommonResponse(result));
    }

    /**
     * @name : approvalDocument
     * @param : UpsertDocumentDto
     * @return : Long
     * @description : 문서 결재 상신
     */
    @PutMapping("/approval")
    public ResponseEntity<?> approvalDocument(@RequestBody UpsertDocumentDto dto) {
        Long result = documentService.approvalDocument(dto);

        return ResponseEntity.ok().body(new CommonResponse(result));
    }

    /**
     * @name : approvalGroupReject
     * @param : UpdateDocumentApprovalGroupDto
     * @return : Long
     * @description : 문서 결재 그룹 결재자 거절
     */
    @PutMapping("{id}/approval/reject")
    public ResponseEntity<?> approvalGroupReject(@PathVariable Long id) {
        Long result = documentService.approvalGroupReject(id);

        return ResponseEntity.ok().body(new CommonResponse(result));
    }

    /**
     * @name : approveGroupConfirm
     * @param : UpdateDocumentApprovalGroupDto
     * @return : Long
     * @description : 문서 결재 그룹 결재자 승인
     */
    @PutMapping("{id}/approval/approve")
    public ResponseEntity<?> approveGroupConfirm(@PathVariable Long id) {
        Long result = documentService.approveGroupConfirm(id);

        return ResponseEntity.ok().body(new CommonResponse(result));
    }

}
