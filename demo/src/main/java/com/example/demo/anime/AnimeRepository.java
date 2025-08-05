package com.example.demo.anime;

import com.example.demo.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> findByNameIgnoreCase(String name);
}
