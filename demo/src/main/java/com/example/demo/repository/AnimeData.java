package com.example.demo.repository;

import com.example.demo.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AnimeData {
    private   List<Anime> animes = new ArrayList<>();
     {
        var anime1 = new Anime(1L, "red");
        var anime2 = new Anime(2L, "addd");
        animes.addAll(List.of(anime1, anime2));
    }

    public  List<Anime> getAnimes() {
        return animes;
    }
}
