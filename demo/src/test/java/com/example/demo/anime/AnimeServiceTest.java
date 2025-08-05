package com.example.demo.anime;

import com.example.demo.commons.AnimeUtils;
import com.example.demo.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService service;
    @Mock
    private AnimeRepository repository;
    private List<Anime> animesList;
    @InjectMocks
    private AnimeUtils animeUtils;

    @BeforeEach
    void init() {
        animesList = animeUtils.newAnimeList();
    }

    @Test
    @DisplayName("findAll returns a list with all animes when name is null")
    @Order(1)
    void findAllNull_Animes_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(animesList);
        var list = service.findyAll(null);
        Assertions.assertThat(list).isNotNull().hasSameElementsAs(animesList);
    }

    @Test
    @DisplayName("findAll returns list with found object when name exists")
    @Order(2)
    void findByName_ReturnsfoundAnimesInList_WhenNameIsfound() {
        var anime = animesList.getFirst();
        var expectedAnimefound = singletonList(anime);
        BDDMockito.when(repository.findByNameIgnoreCase(anime.getName())).thenReturn(expectedAnimefound);
        var animeHound = service.findyAll(anime.getName());
        Assertions.assertThat(animeHound).containsAll(expectedAnimefound);
    }

    @Test
    @DisplayName("findAll returns empty list when name is not found")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotfound() {
        var name = "not hound";
        BDDMockito.when(repository.findByNameIgnoreCase(name)).thenReturn(emptyList());
        var anime = service.findyAll(name);
        Assertions.assertThat(anime).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById returns a anime with given id")
    @Order(4)
    void findById_ReturnsAnimeById_WhenSuccessful() {
        var expectedAnime = animesList.getFirst();
        BDDMockito.when(repository.findById(expectedAnime.getId())).thenReturn(Optional.of(expectedAnime));
        var anime = service.findByIdOrElseTrow(expectedAnime.getId());
        Assertions.assertThat(anime).isEqualTo(expectedAnime);

    }

    @Test
    @DisplayName("findById throws ResponseStatusException when anime is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenAnimeIsNotfound() {
        var expectedAnime = animesList.getFirst();
        BDDMockito.when(repository.findById(expectedAnime.getId())).thenReturn(Optional.empty());
        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrElseTrow(expectedAnime.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @DisplayName("save creates a anime")
    @Order(6)
    void save_createsAnime_WhenSuccessful() {
        var animeToSave =animeUtils.newAnimeToSave();
        BDDMockito.when(repository.save(animeToSave)).thenReturn(animeToSave);
        Anime savedAnime = service.save(animeToSave);
        Assertions.assertThat(savedAnime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes a anime")
    @Order(7)
    void delete_RemoveAnime_WhenSuccessful() {
        var animeToDelete = animesList.getFirst();
        BDDMockito.when(repository.findById(animeToDelete.getId())).thenReturn(Optional.of(animeToDelete));
        service.delete(animeToDelete.getId());
        BDDMockito.doNothing().when(repository).delete(animeToDelete);
        Assertions.assertThatNoException().isThrownBy(() -> service.delete(animeToDelete.getId()));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when anime is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var animeToDelete = animesList.getFirst();
        BDDMockito.when(repository.findById(animeToDelete.getId())).thenReturn(Optional.empty());
        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(animeToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @DisplayName("update updates a anime")
    @Order(9)
    void update_UpdatesAnimeWhenSuccessful() {
        var animeToUpDate = animesList.getFirst();
        animeToUpDate.setName("kkkkkk");
        BDDMockito.when(repository.findById(animeToUpDate.getId())).thenReturn(Optional.of(animeToUpDate));
        BDDMockito.when(repository.save(animeToUpDate)).thenReturn(animeToUpDate);
        Assertions.assertThatNoException().isThrownBy(() -> service.updateForId(animeToUpDate));
    }

    @Test
    @DisplayName("update throws ResponseStatusException when producer is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenAnimeIsNotFound() {
        var animeToUpDate = animesList.getFirst();
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThatException()
                .isThrownBy(() -> service.updateForId(animeToUpDate))
                .isInstanceOf(ResponseStatusException.class);

    }
}