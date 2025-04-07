package com.hockeymanager.application.news.repositories;

import com.hockeymanager.application.news.models.News;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NewsRepository extends JpaRepository<News, String>, JpaSpecificationExecutor<News> {
    @Query(value = "SELECT * FROM news WHERE dynasty_id = ?1 AND dismissed IS FALSE", nativeQuery = true)
    List<News> findAllByDynastyId(String dynastyId);

    @Modifying
    @Query(value = "UPDATE news SET dismissed = true WHERE id = ?1", nativeQuery = true)
    void dismissById(String newsId);

    @Modifying
    @Query(value = "DELETE FROM news WHERE dynasty_id = ?1", nativeQuery = true)
    void deleteAllByDynastyId(String dynastyId);
}
