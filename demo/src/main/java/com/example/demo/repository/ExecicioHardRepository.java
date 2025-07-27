package com.example.demo.repository;

import com.example.demo.domain.Exercicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor

public class ExecicioHardRepository {
    private final ExercicioData exercicioData;

    public List<Exercicio> listALl() {
        return exercicioData.lista();
    }

    public List<Exercicio> findByName(String name) {
        return exercicioData.lista().stream().filter(exercicio -> exercicio.getName().equalsIgnoreCase(name)).toList();
    }
        public Optional<Exercicio> findById(Long id){
     return exercicioData.lista().stream().filter(exercicio -> exercicio.getId().equals(id)).findFirst();
    }
    public Exercicio save(Exercicio exercicio) {
        exercicioData.lista().add(exercicio);
        return exercicio;
    }
    public void delete(Exercicio exercicio) {
        exercicioData.lista().remove(exercicio);
    }
    public void update(Exercicio exercicio) {
        delete(exercicio);
        save(exercicio);
    }




}
