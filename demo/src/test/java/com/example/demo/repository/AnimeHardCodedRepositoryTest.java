package com.example.demo.repository;

import com.example.demo.commons.AnimeUtils;
import com.example.demo.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeHardCodedRepositoryTest {
    @InjectMocks
    private AnimeHardCodedRepository repository;
    @Mock
    private AnimeData animeData;
    private  List<Anime> animes;
    @InjectMocks
    private AnimeUtils animeUtils;
    @BeforeEach
        void init(){
        animes = animeUtils.newAnimeList();
    }
    @Test
    @DisplayName("hindAll returns a list with all animes")
    @Order(1)
    void hindAll (){
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
        var all = repository.findAll();
        Assertions.assertThat(all).isNotNull().hasSameElementsAs(animes);
    }
    @Test
    @DisplayName("hindById returns a anime with given id")
    @Order(2)
    void hindById(){
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
        Anime first = animes.getFirst();
        var all = repository.findById(first.getId());
        Assertions.assertThat(all).isPresent().contains(first);
    }
    @Test
    @DisplayName("hindByNameNull")
    @Order(3)
    void hindByNameNull(){
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
        var all = repository.findByName(null);
        Assertions.assertThat(all).isNotNull().isEmpty();
    }
    @Test
    @DisplayName("hindByNameHound")
    @Order(4)
    void hindByNameHound(){
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
        var first = animes.getFirst();
        var all = repository.findByName(first.getName());
        Assertions.assertThat(all).hasSize(1).contains(first);
    }
    @Test
    @DisplayName("save")
    @Order(5)
    void save(){
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
        Anime build = animeUtils.newAnimeToSave();
        var all = repository.save(build);
        Assertions.assertThat(all).isEqualTo(build).hasNoNullFieldsOrProperties();
        Optional<Anime> byId = repository.findById(all.getId());
        Assertions.assertThat(byId).isPresent().contains(all);
    }
    @Test
    @DisplayName("delete")
    @Order(6)
    void delete(){
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
        var first = animes.getFirst();
        repository.delete(first);
        var all = repository.findAll();
        Assertions.assertThat(all).isNotEmpty().doesNotContain(first);
    }
    @Test
    @DisplayName("update")
    @Order(7)
    void update(){
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
        var first = animes.getFirst();
        first.setName("red");
        repository.update(first);
        Assertions.assertThat(this.animes).contains(first);
        Optional<Anime> byId = repository.findById(first.getId());
        Assertions.assertThat(byId).isPresent();
        Assertions.assertThat(byId.get().getName()).isEqualTo(first.getName());
    }

}