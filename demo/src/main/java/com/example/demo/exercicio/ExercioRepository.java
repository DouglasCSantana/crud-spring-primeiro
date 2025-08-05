package com.example.demo.exercicio;

import com.example.demo.domain.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExercioRepository extends JpaRepository<Exercicio,Long> {
    List<Exercicio> findByNameIgnoreCase(String name);
}
