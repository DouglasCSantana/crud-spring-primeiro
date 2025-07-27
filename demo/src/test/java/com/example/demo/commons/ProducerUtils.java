package com.example.demo.commons;

import com.example.demo.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Component
public class ProducerUtils {
   public List<Producer>  newProducerList(){
       var dateTime = "2025-02-01T21:41:09.6001148";
       var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
       var localDateTime = LocalDateTime.parse(dateTime, formatter);

       var anime1 = Producer.builder().id(1L).name("mappa").localDateTime(localDateTime).build();
       var anime2 = Producer.builder().id(2L).name("animation").localDateTime(localDateTime).build();
       return new ArrayList<>(List.of(anime1, anime2));
   }
   public Producer newProducerToSave(){
      return Producer.builder().id(99L).name("MAPPA").localDateTime(LocalDateTime.now()).build();
   }
}
