package com.example.demo.commons;

import com.example.demo.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AnimeUtils {
    public List<Anime> newAnimeList(){
        Anime build1 = Anime.builder().id(1L).name("asddsa").build();
        Anime build2 = Anime.builder().id(2L).name("gfhfg").build();
        return new ArrayList<>(List.of(build1,build2));
    }
    public Anime newAnimeToSave(){
       return Anime.builder().id(99L).name("ok").build();
    }
}
