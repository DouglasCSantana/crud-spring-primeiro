package com.example.demo.exercicio;

import com.example.demo.domain.Exercicio;
import com.example.demo.exception.NotFoundException;
//import com.example.demo.repository.ExecicioHardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ExercicioService {
    private final ExercioRepository repository;

    public List<Exercicio> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByNameIgnoreCase(name);
    }
    public Exercicio findByIdTrows(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Exercicio Not Found"));
    }

    public Exercicio save(Exercicio exercicio) {
        return repository.save(exercicio);
    }
    public void delete(Long id) {
        Exercicio byIdTrows = findByIdTrows(id);
        repository.delete(byIdTrows);
    }
    public void update(Exercicio exercicio) {
        findByIdTrows(exercicio.getId());
        repository.save(exercicio);

    }
}
