package com.scaler.blogapi.commons;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Getter(AccessLevel.PUBLIC)
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    Date createdAt;
}
