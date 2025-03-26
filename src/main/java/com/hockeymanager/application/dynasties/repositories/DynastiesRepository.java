package com.hockeymanager.application.dynasties.repositories;

import com.hockeymanager.application.dynasties.models.Dynasty;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DynastiesRepository extends JpaRepository<Dynasty, String>, JpaSpecificationExecutor<Dynasty> {
    List<Dynasty> findAll();
}
