package com.example.zai.repositories;

import com.example.zai.models.Information;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InformationRepository extends JpaRepository<Information, Long> {
    Information findByName(String name);

    List<Information> findByCategory(String category);
}
