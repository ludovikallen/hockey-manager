package com.hockeymanager.application.data;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();
}