package com.example.demo.controller;

import com.example.demo.mapper.ProducerMapper;
import com.example.demo.request.ProducerPostRequest;
import com.example.demo.request.ProducerPutRequest;
import com.example.demo.response.ProducerGetResponse;
import com.example.demo.response.ProducerPostResponse;
import com.example.demo.service.ProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@RequiredArgsConstructor


public class ProducerController {
    private  final ProducerMapper MAPPER;
    private static final Logger log = LoggerFactory.getLogger(ProducerController.class);
    private final ProducerService service;


    @GetMapping
    public  ResponseEntity<List<ProducerGetResponse>> findAll(@RequestParam(required = false) String name) {
        log.debug("name{}",name);
        var producers = service.findAll(name);

        var response = MAPPER.toProducerList(producers);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public  ResponseEntity<ProducerGetResponse> findById(@PathVariable Long id) {
        log.debug("encontrar por id{}", id);
        var producer = service.findyByIdOrElseThrow(id);

        ProducerGetResponse producerGetResponse = MAPPER.toProducerGetResponse(producer);

        return ResponseEntity.ok(producerGetResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "x-api-key=1234")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody @Valid ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {
        log.info("{}",headers);
        var producer = MAPPER.toProducer(producerPostRequest);
        var produserSaved = service.save(producer);
        ProducerPostResponse producerPostResponse = MAPPER.toProducerPostResponse(produserSaved);
//        var producer = Producer.builder()
//
//                .id(ThreadLocalRandom.current().nextLong(500))
//                .localDateTime(LocalDateTime.now())
//                .name(producerPostRequest.getName()).build();
//        Producer.lista().add(producer);
//        var httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization","My key");
//        return ResponseEntity.ok(producer);
//        var response = ProducerGetResponse.builder()
//                .id(producer.getId())
//                .name(producer.getName())
//                .localDateTime(producer.getLocalDateTime()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(producerPostResponse);
//        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void>deleteById(@PathVariable Long id){
        log.debug("delete for id{}",id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
    @PutMapping()
    public ResponseEntity<Void>update(@RequestBody @Valid ProducerPutRequest request){
        log.debug("update{}",request);

        var producerToUpdate = MAPPER.toProducer(request);

        service.update(producerToUpdate);

        return ResponseEntity.noContent().build();
    }
}
