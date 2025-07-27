package com.example.demo.service;

import com.example.demo.domain.Exercicio;
import com.example.demo.repository.ExecicioHardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ExercicioService {
    private final ExecicioHardRepository repository;

    public List<Exercicio> findAll(String name) {
        return name == null ? repository.listALl() : repository.findByName(name);
    }
    public Exercicio findByIdTrows(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
        repository.update(exercicio);

    }
}
