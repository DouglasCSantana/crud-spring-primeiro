package com.example.demo.repository;

import com.example.demo.domain.Exercicio;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExercicioData {
    private final List<Exercicio> exercicio = new ArrayList<>();

    {
        var le1 = Exercicio.builder().id(1L).name("asdas").build();
        var le2 =Exercicio.builder().id(2L).name("fgfg").build();
        exercicio.addAll(List.of(le1,le2));
    }
    public List<Exercicio>lista(){
        return exercicio;
    }

}



