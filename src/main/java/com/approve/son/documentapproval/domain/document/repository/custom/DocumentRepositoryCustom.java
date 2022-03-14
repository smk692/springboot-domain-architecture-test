package com.approve.son.documentapproval.domain.document.repository.custom;

import com.approve.son.documentapproval.domain.document.dto.DocumentDto;
import com.approve.son.documentapproval.global.entity.EnumType;

import java.util.List;

public interface DocumentRepositoryCustom {

    List<DocumentDto> selectDocumentList(EnumType.DocumentSearchType documentSearchType);
}
