package com.approve.son.documentapproval.domain.document.repository;

import com.approve.son.documentapproval.domain.document.domain.DocumentApprovalGroup;
import com.approve.son.documentapproval.domain.document.repository.custom.DocumentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentApprovalGroupRepository extends JpaRepository<DocumentApprovalGroup, Long>, DocumentRepositoryCustom {

}
