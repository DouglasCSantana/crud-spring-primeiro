package com.example.demo.repository;

import com.example.demo.domain.Anime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class AnimeHardCodedRepository {
    private final AnimeData AnimeData;

    public List<Anime> findAll(){
        return AnimeData.getAnimes();
    }
    public List<Anime> findByName(String name){
        return AnimeData.getAnimes().stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }
    public Optional<Anime>findById(Long id){
       return AnimeData.getAnimes().stream().filter(anime -> anime.getId().equals(id)).findFirst();
    }
    public Anime save(Anime anime){
    AnimeData.getAnimes().add(anime);
    return anime;
    }
    public void delete(Anime anime){
        AnimeData.getAnimes().remove(anime);
    }
    public void update(Anime anime){
        delete(anime);
        save(anime);
    }
}
