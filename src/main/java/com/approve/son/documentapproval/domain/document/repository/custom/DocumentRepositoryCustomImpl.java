package com.approve.son.documentapproval.domain.document.repository.custom;

import com.approve.son.documentapproval.domain.document.domain.QDocument;
import com.approve.son.documentapproval.domain.document.domain.QDocumentApprovalGroup;
import com.approve.son.documentapproval.domain.document.dto.DocumentApprovalDto;
import com.approve.son.documentapproval.domain.document.dto.DocumentApprovalGroupDto;
import com.approve.son.documentapproval.domain.document.dto.DocumentDto;
import com.approve.son.documentapproval.global.entity.EnumType;
import com.approve.son.documentapproval.global.system.context.SonContextHolder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.approve.son.documentapproval.global.entity.EnumType.DocumentApprovalGroupStatus.UNCONFIRMED;

@Slf4j
@RequiredArgsConstructor
public class DocumentRepositoryCustomImpl implements DocumentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DocumentDto> selectDocumentList(EnumType.DocumentSearchType documentSearchType) {
        QDocument qDocument = new QDocument("D");
        return queryFactory.select(Projections.fields(DocumentDto.class
                , qDocument.id.as("id")
                , qDocument.title.as("title")
                , qDocument.documentType.as("documentType")
                , qDocument.contents.as("contents")
                , qDocument.documentStatus.as("documentStatus")
                , Projections.fields(DocumentApprovalDto.class
                        , qDocument.documentApproval.approvalCompleteDate.as("approvalCompleteDate")
                        , qDocument.documentApproval.approvalRejectDate.as("approvalRejectDate")
                        , qDocument.documentApproval.approvalSendDate.as("approvalSendDate")
                        , qDocument.documentApproval.approvalSendMemberId.as("approvalSendMemberId").as("documentApproval")
                )
                , qDocument.createdAt.as("createdAt")
                , qDocument.createdMemberId.as("createdMemberId")
                , qDocument.updatedAt.as("updatedAt")
                , qDocument.updatedMemberId.as("updatedMemberId")
        ))
                .from(qDocument)
                .fetch();
    }

    public BooleanExpression eqSearchOption(EnumType.DocumentSearchType documentSearchType, QDocument qDocument) {
        Long myId = SonContextHolder.getContext().getCurrentMemberId();

        if (documentSearchType.equals(EnumType.DocumentSearchType.OUTBOX)) {
            return qDocument.createdMemberId.eq(myId);
        }

        QDocumentApprovalGroup qDocumentApprovalGroup = new QDocumentApprovalGroup("DAG");
        if (documentSearchType.equals(EnumType.DocumentSearchType.INBOX)) {
            // 문서 ID (exists custom 속도 개선)
            List<Long> documentId = queryFactory.select(qDocumentApprovalGroup.documentId)
                    .from(qDocumentApprovalGroup)
                    .where(qDocumentApprovalGroup.approvalMemberId.eq(myId)
                            .and(qDocumentApprovalGroup.documentApprovalGroupStatus.eq(UNCONFIRMED))
                    )
                    .distinct()
                    .fetch();
            return qDocument.id.in(documentId);
        }

        if (documentSearchType.equals(EnumType.DocumentSearchType.ARCHIVE)) {
            // 문서 ID (exists custom 속도 개선)
            List<Long> documentId = queryFactory.select(qDocumentApprovalGroup.documentId)
                    .from(qDocumentApprovalGroup)
                    .where(qDocumentApprovalGroup.approvalMemberId.eq(myId)
                            .and(qDocumentApprovalGroup.documentApprovalGroupStatus.ne(UNCONFIRMED))
                    )
                    .distinct()
                    .fetch();

            return qDocument.id.in(documentId);
        }
        return null;
    }
}
