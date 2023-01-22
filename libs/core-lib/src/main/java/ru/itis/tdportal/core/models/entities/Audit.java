package ru.itis.tdportal.core.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Audited
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Audit<U extends Serializable> implements Serializable {

    @CreatedBy
    @Column(name = "creator_id")
    protected U creatorId;

    @CreatedDate
    @Column(name = "created_at")
    protected Instant createdAt;

    @LastModifiedBy
    @Column(name = "last_modifier_id")
    protected U lastModifierId;

    @LastModifiedDate
    @Column(name = "last_modified_at")
    protected Instant lastModifiedAt;
}