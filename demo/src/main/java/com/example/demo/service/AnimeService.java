package com.example.demo.service;

import com.example.demo.domain.Anime;
import com.example.demo.repository.AnimeHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AnimeService {
   private final AnimeHardCodedRepository repository;

    public List<Anime> findyAll(String name) {
       return name == null ?repository.findAll(): repository.findByName(name);
    }
    public Anime findByIdOrElseTrow(Long id){
      return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Producer not Found"));
    }
    public Anime save(Anime anime){
       return repository.save(anime);
    }
    public void delete(Long id){
        var anime = findByIdOrElseTrow(id);
        repository.delete(anime);
    }
    public void updateForId(Anime animeToUpdate){
        assertAnimeExists(animeToUpdate.getId());

        repository.update(animeToUpdate);
    }
    public void assertAnimeExists(Long id){
        findByIdOrElseTrow(id);
    }
}
