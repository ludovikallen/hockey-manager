package com.hockeymanager.application.news.services;

import com.hockeymanager.application.news.models.News;
import com.hockeymanager.application.news.repositories.NewsRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@BrowserCallable
@AnonymousAllowed
@Service
@AllArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

    @NonNull
    public List<@NonNull News> findAllByDynastyId(String dynastyId) {
        return newsRepository.findAllByDynastyId(dynastyId);
    }

    @Transactional
    public void dismissById(String newsId) {
        newsRepository.dismissById(newsId);
    }
}