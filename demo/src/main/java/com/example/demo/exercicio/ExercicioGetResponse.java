package com.example.demo.exercicio;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ExercicioGetResponse {
    private Long id;
    private String name;
}
