package com.approve.son.documentapproval.domain.document.domain;

import com.approve.son.documentapproval.domain.document.dto.*;
import com.approve.son.documentapproval.global.entity.BaseEntity;
import com.approve.son.documentapproval.global.entity.EnumType;
import com.approve.son.documentapproval.global.system.constants.ErrorCode;
import com.approve.son.documentapproval.global.system.context.SonContext;
import com.approve.son.documentapproval.global.system.context.SonContextHolder;
import com.approve.son.documentapproval.global.system.exception.handler.BadRequestException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.approve.son.documentapproval.global.entity.EnumType.DocumentApprovalGroupStatus.APPROVAL;
import static com.approve.son.documentapproval.global.entity.EnumType.DocumentApprovalGroupStatus.UNCONFIRMED;
import static com.approve.son.documentapproval.global.entity.EnumType.DocumentStatus.*;
import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "document")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Document extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // 제목
    @Column(name = "title", length = 50)
    private String title;

    // enum('휴가신청서', '지출결의서')
    @Column(name = "type")
    @Enumerated(STRING)
    private EnumType.DocumentType documentType;

    // 문서 내용
    @Column(name = "contents")
    private String contents;

    // 문서 상태 (enum 저장, 결재 진행중, 상신 반려, 결재 완료)
    @Column(name = "document_status")
    @Enumerated(STRING)
    private EnumType.DocumentStatus documentStatus;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "approvalSendMemberId", column = @Column(name = "approval_send_member_id")),
            @AttributeOverride(name = "approvalSendDate", column = @Column(name = "approval_send_date")),
            @AttributeOverride(name = "approvalCompleteDate", column = @Column(name = "approval_complete_date")),
            @AttributeOverride(name = "approvalRejectDate", column = @Column(name = "approval_reject_date"))
    })
    // 문서 결재 관련 객체
    private DocumentApproval documentApproval;

    // 문서 결재자 그룹
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "document_id")
    private List<DocumentApprovalGroup> documentApprovalGroup = new ArrayList<>();

    // 문서 생성
    public Document(String title, EnumType.DocumentType documentType, String contents, List<DocumentApprovalGroupDto> documentApprovalGroupDto) {

        this.title = title;
        this.documentType = documentType;
        this.contents = contents;
        this.documentStatus = EnumType.DocumentStatus.SAVE;
        this.documentApprovalGroup = approvalGroupList(documentApprovalGroupDto);
        this.documentApprovalGroup.clear();

        this.validate();
    }

    // Entity -> Dto
    public DocumentDto toDto() {
        return new DocumentDto(
                this.id,
                this.title,
                this.documentType,
                this.contents,
                this.documentStatus,
                new DocumentApprovalDto(
                        this.documentApproval == null ? null : this.documentApproval.getApprovalSendMemberId(),
                        this.documentApproval == null ? null : this.documentApproval.getApprovalSendDate(),
                        this.documentApproval == null ? null : this.documentApproval.getApprovalCompleteDate(),
                        this.documentApproval == null ? null : this.documentApproval.getApprovalRejectDate()
                ),
                this.documentApprovalGroup
                        .stream()
                        .map(DocumentApprovalGroup::toDto)
                        .collect(Collectors.toList()),
                this.createdAt,
                this.updatedAt,
                this.createdMemberId,
                this.updatedMemberId
        );

    }

    // 문서 저장
    public void documentUpdate(UpsertDocumentDto dto) {

        // 저장 상태가 아닐때는 에러 - 상태 값 되돌아가는 이슈 방지
        if (!this.documentStatus.equals(SAVE)) {
            throw new BadRequestException(ErrorCode.INVALID_NOT_SAVE_DOCUMENT_STATUS);
        }

        this.title = dto.getTitle();
        this.documentType = dto.getDocumentType();
        this.contents = dto.getContents();
        this.documentApprovalGroup = this.approvalGroupList(dto.getDocumentApprovalGroupDto());

        this.validate();

    }

    // 문서 결재 상신
    public void documentApproval(UpsertDocumentDto dto) {
        this.validate();

        this.title = dto.getTitle();
        this.documentType = dto.getDocumentType();
        this.contents = dto.getContents();
        this.documentStatus = EnumType.DocumentStatus.PROGRESS;
        this.documentApproval = new DocumentApproval(this.documentStatus);
        this.documentApprovalGroup = this.approvalGroupList(dto.getDocumentApprovalGroupDto());
    }

    public void validate() {

        if (this.title.length() > 50) {
            throw new BadRequestException(ErrorCode.MAX_INPUT_DOCUMENT_TITLE);
        }

        List<Long> idList = this.documentApprovalGroup
                .stream()
                .map(DocumentApprovalGroup::getApprovalMemberId)
                .collect(Collectors.toList());
        List<Long> sortList = this.documentApprovalGroup
                .stream()
                .map(DocumentApprovalGroup::getSort)
                .collect(Collectors.toList());

        this.duplicateApprovalMemberId(idList);
        this.duplicateApprovalSort(sortList);
    }
    public void duplicateApprovalMemberId(List<Long> idList) {
        Set<Long> setId = extractionDuplicate(idList);

        if (setId.size() > 0) {
            throw new BadRequestException(ErrorCode.DUPLICATE_APPROVAL_MEMBER_ID, stringDuplicateId(setId));
        }
    }

    public void duplicateApprovalSort(List<Long> idList) {
        Set<Long> setId = extractionDuplicate(idList);

        if (setId.size() > 0) {
            throw new BadRequestException(ErrorCode.DUPLICATE_APPROVAL_SORT, stringDuplicateId(setId));
        }
    }

    public Set<Long> extractionDuplicate(List<Long> idList) {
        Set<Long> duplicateId = new HashSet<>();

        for (Long id : idList) {
            if (idList.indexOf(id) != idList.lastIndexOf(id)) {
                duplicateId.add(id);
            }
        }
        return duplicateId;
    }
    public String stringDuplicateId(Set<Long> duplicateId) {
        // 중복된 데이터 출력
        StringBuilder str = new StringBuilder();
        for (Long id : duplicateId) {
            if(str.length() == 0) {
                str.append(id);
                continue;
            }
            str.append(", ");
            str.append(id);
        }
        return str.toString();
    }

    private List<DocumentApprovalGroup> approvalGroupList (List<DocumentApprovalGroupDto> list) {
        this.documentApprovalGroup.clear();

        List<DocumentApprovalGroup> approvalGroupList = this.documentApprovalGroup;
        for (DocumentApprovalGroupDto data : list) {
            approvalGroupList.add(
                    new DocumentApprovalGroup(
                            data.getApprovalMemberId(),
                            data.getComment(),
                            data.getSort()
                    ));
        }
        return approvalGroupList;
    }

    // 결재자 반려
    public void approvalGroupReject() {
        DocumentApprovalGroup updateData = this.approveGroupUpdateValidate();
        updateData.rejectUpdate();

        // 결재자 반려
        this.documentStatus = REJECT;
        this.documentApproval.documentRejectUpdate();

    }

    // 결재자 승인
    public void approveGroupConfirm() {
        DocumentApprovalGroup updateData = this.approveGroupUpdateValidate();
        updateData.approvalUpdate();

        Long size = documentApprovalGroup
                .stream()
                .filter(e-> e.getDocumentApprovalGroupStatus().equals(APPROVAL))
                .count();

        // 결재자 전체 승인
        if (size == documentApprovalGroup.size()) {
            this.documentStatus = COMPLETION;
            this.documentApproval.documentApprovalUpdate();
        }
    }

    public DocumentApprovalGroup approveGroupUpdateValidate() {
        Long currentId = SonContextHolder.getContext().getCurrentMemberId();

        if(!this.documentStatus.equals(PROGRESS)) {
            throw new BadRequestException(ErrorCode.NOT_UPDATE_DATA_ONLY_PROGRESS);
        }
        DocumentApprovalGroup updateData = documentApprovalGroup
                .stream()
                .filter(e-> e.getApprovalMemberId().equals(currentId))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_UPDATE_DATA_ONLY_MY_ID));

        DocumentApprovalGroup sortCheck = documentApprovalGroup
                .stream()
                .filter(e-> e.getDocumentApprovalGroupStatus().equals(UNCONFIRMED))
                .sorted(Comparator.comparing(DocumentApprovalGroup::getSort))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_FIND_DATA));

        if (updateData.getId().equals(sortCheck.getId())) {
            throw new BadRequestException(ErrorCode.NOT_UPDATE_DATA_FIND_SORT);
        }

        if (!updateData.getApprovalMemberId().equals(currentId)) {
            throw new BadRequestException(ErrorCode.NOT_UPDATE_DATA_ONLY_MY_ID);
        }

        return updateData;

    }
}
