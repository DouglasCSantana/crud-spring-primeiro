package com.example.demo.mapper;

import com.example.demo.domain.Anime;
import com.example.demo.request.AnimePostRequest;
import com.example.demo.request.AnimePutRequest;
import com.example.demo.response.AnimeGetResponse;
import com.example.demo.response.AnimePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimeMapper {

    @Mapping(target = "id",expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(500))")
    Anime toAnime(AnimePostRequest postRequest);

    Anime toAnime(AnimePutRequest request);


    AnimePostResponse toAnimePostResponse(Anime  anime);

    AnimeGetResponse toAnimeGetResponseList(Anime Anime);

    List<AnimeGetResponse> toAnimeGetResponseList(List<Anime> animes);

}
