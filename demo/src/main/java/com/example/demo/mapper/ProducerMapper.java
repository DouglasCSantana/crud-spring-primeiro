package com.example.demo.mapper;


import com.example.demo.domain.Producer;
import com.example.demo.request.ProducerPostRequest;
import com.example.demo.request.ProducerPutRequest;
import com.example.demo.response.ProducerGetResponse;
import com.example.demo.response.ProducerPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProducerMapper {
    @Mapping(target ="localDateTime",expression =  "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id",expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(500))")
    Producer toProducer(ProducerPostRequest postRequest);
    Producer toProducer(ProducerPutRequest request);
    ProducerPostResponse toProducerPostResponse(Producer producer);


    ProducerGetResponse toProducerGetResponse(Producer Producer);

    List<ProducerGetResponse> toProducerList(List<Producer>producers);
}

