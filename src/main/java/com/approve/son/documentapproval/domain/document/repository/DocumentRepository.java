package com.approve.son.documentapproval.domain.document.repository;

import com.approve.son.documentapproval.domain.document.domain.Document;
import com.approve.son.documentapproval.domain.document.repository.custom.DocumentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long>, DocumentRepositoryCustom {

}
