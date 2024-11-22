package com.simonlangbak.agendo.domain;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Date created;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updated;

}
