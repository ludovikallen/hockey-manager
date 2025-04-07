package com.hockeymanager.application.news.models;

import com.hockeymanager.application.data.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

@Getter
@Setter
@Entity
@Table(name = "news")
public class News extends BaseEntity {
    @NonNull
    private String dynastyId;

    @NonNull
    private int turnId;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private boolean dismissed = false;
}
