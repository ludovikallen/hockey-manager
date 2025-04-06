package com.hockeymanager.application.data;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @Column(updatable = false, nullable = false, columnDefinition = "TEXT")
    @NonNull
    private String id = UUID.randomUUID().toString();
}