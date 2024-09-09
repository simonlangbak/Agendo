package com.simonlangbak.agendo.domain;

import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

public abstract class BaseEntity {

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Date created;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updated;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
