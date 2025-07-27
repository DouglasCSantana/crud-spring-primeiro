package com.example.demo.controller;


import com.example.demo.mapper.AnimeMapper;
import com.example.demo.request.AnimePostRequest;
import com.example.demo.request.AnimePutRequest;
import com.example.demo.response.AnimeGetResponse;
import com.example.demo.response.AnimePostResponse;
import com.example.demo.service.AnimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/animes")


public class AnimeController {
    private final AnimeMapper MAPPER;
    private final AnimeService service;



    @GetMapping
    public  ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.debug("encontrar por name{}", name);
        var lista = service.findyAll(name);

        var response = MAPPER.toAnimeGetResponseList(lista);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public  ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.debug("encontrar por id{}", id);
        var anime = service.findByIdOrElseTrow(id);

        var animeGetResponse = MAPPER.toAnimeGetResponseList(anime);

        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping
    public  ResponseEntity<AnimePostResponse> save(@RequestBody @Valid AnimePostRequest request) {
        log.debug("encontrar por re{}", request);
        var anime = MAPPER.toAnime(request);
        var animeSaved = service.save(anime);

        var response = MAPPER.toAnimePostResponse(animeSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void>deleteForId(@PathVariable Long id){
        log.info("delete for id {}",id);
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
    @PutMapping()
    public ResponseEntity<Void>update(@RequestBody @Valid AnimePutRequest request){
        log.info("update {}",request);

        var animeUpdate = MAPPER.toAnime(request);

        service.updateForId(animeUpdate);

        return ResponseEntity.noContent().build();
    }
}
