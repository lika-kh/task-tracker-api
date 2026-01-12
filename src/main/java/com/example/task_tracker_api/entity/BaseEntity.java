package com.example.task_tracker_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;


    @PrePersist
    protected void onCreate(){
        LocalDateTime now=LocalDateTime.now();
        this.createDate=now;
        this.updateDate= now;
    }

    @PreUpdate
    protected void OnUpdate(){
        this.updateDate=LocalDateTime.now();
    }

}
