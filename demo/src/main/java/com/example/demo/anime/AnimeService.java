package com.example.demo.anime;

import com.example.demo.domain.Anime;
import com.example.demo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AnimeService {
   private final AnimeRepository repository;

    public List<Anime> findyAll(String name) {
       return name == null ?repository.findAll(): repository.findByNameIgnoreCase(name);
    }
    public Anime findByIdOrElseTrow(Long id){
      return repository.findById(id).orElseThrow(() -> new NotFoundException("Anime not found"));
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

        repository.save(animeToUpdate);
    }
    public void assertAnimeExists(Long id){
        findByIdOrElseTrow(id);
    }
}
