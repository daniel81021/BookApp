package com.example.BookApp.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDate;

@Getter
@Setter
@Embeddable
public class Audit {

    @Column(name = "created_on", updatable = false)
    private LocalDate createdOn;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @PrePersist
    private void prePersist() {
        createdOn = LocalDate.now();
        updatedOn = createdOn;
    }

    @PreUpdate
    private void preUpdate() {
        updatedOn = LocalDate.now();
    }
}
