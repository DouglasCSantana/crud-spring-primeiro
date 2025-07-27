package com.example.demo.mapper;

import com.example.demo.domain.Exercicio;
import com.example.demo.request.ExercicioPostRequest;
import com.example.demo.request.ExercicioPutRequest;
import com.example.demo.response.ExercicioGetResponse;
import com.example.demo.response.ExercicioPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExercicioMapper{
    @Mapping(target = "id",expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(500))")
    Exercicio postReq(ExercicioPostRequest request);
    ExercicioPostResponse postResponse(Exercicio exercicio);

    Exercicio  putResq(ExercicioPutRequest request);
    List<ExercicioGetResponse> getList(List<Exercicio> exercicio);

    ExercicioGetResponse get(Exercicio exercicio);


}

