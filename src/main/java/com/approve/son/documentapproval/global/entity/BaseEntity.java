package com.approve.son.documentapproval.global.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = true)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    protected LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_member_id", updatable = false, nullable = true)
    protected Long createdMemberId;

    @LastModifiedBy
    @Column(name = "updated_member_id", nullable = true)
    protected Long updatedMemberId;
}
