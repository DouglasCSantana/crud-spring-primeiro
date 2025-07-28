package com.example.demo.controller;

import com.example.demo.commons.AnimeUtils;
import com.example.demo.commons.FilesUtils;
import com.example.demo.domain.Anime;
import com.example.demo.repository.AnimeData;
import com.example.demo.repository.AnimeHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(controllers = AnimeController.class)
@ComponentScan(basePackages = "com.example.demo")
class AnimeControllerTest {
    private static final String URL = "/v1/animes";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnimeData animeData;
    @SpyBean
    private AnimeHardCodedRepository repository;
    private List<Anime> list;
    @Autowired
    private AnimeUtils animeUtils;
    @Autowired
    private FilesUtils filesUtils;

    @BeforeEach
    void init() {
        list = animeUtils.newAnimeList();
    }

    @Test
    void findyAll() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(list);
        String s = filesUtils.readResouserfile

                ("animes/get-animes-null-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(s));
    }

    @Test
    void hindByNameSucess() throws Exception {
        String s = filesUtils.readResouserfile

                ("animes/get-animes-name-200.json");
        BDDMockito.when(animeData.getAnimes()).thenReturn(list);
        var name = "asddsa";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(s));
    }

    @Test
    void hindByNameNotSucess() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(list);
        String s = filesUtils.readResouserfile

                ("animes/get-animes-x-200.json");
        var name = "x";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(s));
    }

    @Test
    void hindByIdSucess() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(list);
        String s = filesUtils.readResouserfile

                ("animes/get-animes-by-id-200.json");
        var id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(s));
    }

    @Test
    void hindByIdNotSucess() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(list);
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void delete_RemoveProducer_WhenSuccessful() throws Exception {
        var id = list.getFirst().getId();
        BDDMockito.when(animeData.getAnimes()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void delete_RemoveProducer_WhenNotSuccessful() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(list);
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Producer not Found"));
    }

    @Test
    void save() throws Exception {
        String s = filesUtils.readResouserfile

                ("animes/pos-request-animes-ok-fields-200.json");
        String s1 = filesUtils.readResouserfile

                ("animes/post-response-animes-ok-201.json");
        var build = animeUtils.newAnimeToSave();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(build);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(s1));
    }

    @Test
    void update() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(list);
        String s = filesUtils.readResouserfile

                ("animes/put-animes-by-id-200.json");

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void Notupdate() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(list);
        String s = filesUtils.readResouserfile

                ("Exercicio/put-request-exercicio-404.json");
        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @ParameterizedTest
    @MethodSource("postinvalidation")
    void saveBadRequest(String fileName, List<String>errors) throws Exception {
        String s = filesUtils.readResouserfile
                ("animes/%s".formatted(fileName));
        var build = animeUtils.newAnimeToSave();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(build);
        var mvcResult =mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
       var resolvedException= mvcResult.getResolvedException();
       Assertions.assertThat(resolvedException).isNotNull();
       Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }
    @ParameterizedTest
    @MethodSource("putinvalidation")
    void putBadRequest(String fileName,List<String>errors) throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(list);
        String s = filesUtils.readResouserfile("animes/%s".formatted(fileName));
        var mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        var resolvedException = mvcResult.getResolvedException();
        Assertions.assertThat(resolvedException).isNotNull();
        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }
    private static List<String>invalidationEr(){
        var erro = "The field 'name' is required";
       return new ArrayList<>(List.of(erro));
    }
    private static Stream<Arguments>postinvalidation(){
        var invalidationEr = invalidationEr();
        return Stream.of(
                Arguments.of("post-request-blank-fields-400.json",invalidationEr),
                Arguments.of("post-request-empty-fields-400.json",invalidationEr)
        );
    }
    private static Stream<Arguments>putinvalidation(){
        var invalidationEr = invalidationEr();
        invalidationEr.add("The field 'id' cannot be null");
        return Stream.of(
                Arguments.of("put-animes-blank-fields-400.json",invalidationEr),
                Arguments.of("put-animes-empty-fields-400.json",invalidationEr));
    }

}