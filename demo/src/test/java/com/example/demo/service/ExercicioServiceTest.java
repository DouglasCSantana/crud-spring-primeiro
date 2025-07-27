package com.example.demo.service;

import com.example.demo.domain.Exercicio;
import com.example.demo.repository.ExecicioHardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@ExtendWith(MockitoExtension.class)
class ExercicioServiceTest {
    private static final Logger log = LoggerFactory.getLogger(ExercicioServiceTest.class);
    @InjectMocks
    private ExercicioService service;
    @Mock
    private ExecicioHardRepository repository;
    private List<Exercicio> list;

    @BeforeEach
    void init() {
        Exercicio build = Exercicio.builder().id(1L).name("asdsad").build();
        Exercicio build1 = Exercicio.builder().id(2L).name("gfdg").build();
        list = new ArrayList<>(List.of(build, build1));
    }

    @Test
    void findByAllSucess() {
        BDDMockito.when(repository.listALl()).thenReturn(list);
        List<Exercicio> all = service.findAll(null);
        Assertions.assertThat(all).isNotNull().hasSameElementsAs(list);
    }

    @Test
    void findByNameNotSucess() {
        var name = "x";
        BDDMockito.when(repository.findByName(name)).thenReturn(emptyList());
        List<Exercicio> all = service.findAll(name);
        Assertions.assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void findByNameSucess() {
        Exercicio first = list.getFirst();
        List<Exercicio> exercicios1 = singletonList(first);
        BDDMockito.when(repository.findByName(first.getName())).thenReturn(exercicios1);
        List<Exercicio> all = service.findAll(first.getName());
        Assertions.assertThat(all).containsAll(exercicios1);
    }

    @Test
    void findByIdSucess() {
        Exercicio first = list.getFirst();
        BDDMockito.when(repository.findById(first.getId())).thenReturn(Optional.of(first));
        Exercicio byIdTrows = service.findByIdTrows(first.getId());
        Assertions.assertThat(byIdTrows).isEqualTo(first);
    }

    @Test
    void findByIdNotSucess() {
        Exercicio first = list.getFirst();
        BDDMockito.when(repository.findById(first.getId())).thenReturn(Optional.empty());
        Assertions.assertThatException().isThrownBy(() -> service.findByIdTrows(first.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void deleteSucess() {
        Exercicio first = list.getFirst();
        BDDMockito.when(repository.findById(first.getId())).thenReturn(Optional.of(first));
        BDDMockito.doNothing().when(repository).delete(first);
        Assertions.assertThatNoException().isThrownBy(() -> service.delete(first.getId()));

    }

    @Test
    void deleteNotSucess() {
        Exercicio first = list.getFirst();
        BDDMockito.when(repository.findById(first.getId())).thenReturn(Optional.empty());
        Assertions.assertThatException().isThrownBy(() -> service.delete(first.getId())).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateNotSucess() {
        Exercicio first = list.getFirst();
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThatException().isThrownBy(() -> service.update(first))
                .isInstanceOf(ResponseStatusException.class);
    }
    @Test
    void updateSucess() {
        Exercicio first = list.getFirst();
        first.setName("sdasdsadsad");
        BDDMockito.when(repository.findById(first.getId())).thenReturn(Optional.of(first));
        BDDMockito.doNothing().when(repository).update(first);
        Assertions.assertThatNoException().isThrownBy(() -> service.update(first));
    }
    @Test
    void saveSucess() {
        Exercicio build = Exercicio.builder().id(99L).name("dffdgf").build();
        BDDMockito.when(repository.save(build)).thenReturn(build);
        Exercicio save = service.save(build);
        Assertions.assertThat(save).hasNoNullFieldsOrProperties().isEqualTo(build);
    }

}
