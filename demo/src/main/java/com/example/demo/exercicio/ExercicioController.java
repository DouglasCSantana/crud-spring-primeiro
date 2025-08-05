package com.example.demo.exercicio;


import com.example.demo.domain.Exercicio;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("v1/exercicios")
@RequiredArgsConstructor

public class ExercicioController {
    private final ExercicioMapper mapper;
    private final ExercicioService service;

    @GetMapping
    public ResponseEntity<List<ExercicioGetResponse>> findAll(@RequestParam(required = false)  String name){
        List<Exercicio> all = service.findAll(name);
        List<ExercicioGetResponse> list = mapper.getList(all);
        return ResponseEntity.ok(list);
    }
    @GetMapping("{id}")
    public ResponseEntity<ExercicioGetResponse> findById(@PathVariable  Long id){
        Exercicio byIdTrows = service.findByIdTrows(id);
        ExercicioGetResponse exercicioGetResponse = mapper.get(byIdTrows);
        return ResponseEntity.ok(exercicioGetResponse);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(Exercicio exercicio){
        Exercicio byIdTrows = service.findByIdTrows(exercicio.getId());
        service.delete(byIdTrows.getId());
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<ExercicioPostResponse>save(@RequestBody @Valid ExercicioPostRequest request){
        Exercicio exercicio = mapper.postReq(request);
        Exercicio save = service.save(exercicio);
        ExercicioPostResponse exercicioPostResponse = mapper.postResponse(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(exercicioPostResponse);
    }
    @PutMapping
    public ResponseEntity<Void>update(@RequestBody @Valid ExercicioPutRequest request){
        Exercicio exercicio = mapper.putResq(request);
        service.update(exercicio);
        return ResponseEntity.noContent().build();
    }


}
