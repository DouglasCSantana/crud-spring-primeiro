package com.example.demo.repository;

import com.example.demo.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Component
public class ProducerData {
    private   List<Producer> producers = new ArrayList<>();
     {
        var anime1 = Producer.builder().id(1L).name("mappa").localDateTime(LocalDateTime.now()).build();
        var anime2 = Producer.builder().id(2L).name("animation").localDateTime(LocalDateTime.now()).build();
        producers.addAll(List.of(anime1, anime2));
    }

    public List<Producer> getProducers() {
        return producers;
    }
}
