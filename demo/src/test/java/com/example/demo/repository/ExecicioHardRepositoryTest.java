package com.example.demo.repository;

import com.example.demo.domain.Exercicio;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ExecicioHardRepositoryTest {
    @InjectMocks
    private ExecicioHardRepository repository;
    @Mock
    private ExercicioData exercicioData;
    private List<Exercicio> exercicios;

    @BeforeEach
    void init() {
        Exercicio build = Exercicio.builder().id(1L).name("edwe").build();
        Exercicio build1 = Exercicio.builder().id(2L).name("asdasd").build();
        exercicios = new ArrayList<>(List.of(build, build1));
    }

    @Test
    void findAllSucess() {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        List<Exercicio> exercicios1 = repository.listALl();
        Assertions.assertThat(exercicios1).isNotNull().hasSize(exercicios.size());
    }

    @Test
    void findByNameSucess() {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        Exercicio first = exercicios.getFirst();
        List<Exercicio> exercicios1 = repository.findByName(first.getName());
        Assertions.assertThat(exercicios1).contains(first);
    }

    @Test
    void findByNameNotSucess() {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        List<Exercicio> exercicios1 = repository.findByName(null);
        Assertions.assertThat(exercicios1).isNotNull().isEmpty();
    }

    @Test
    void findByIdSucess() {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        Exercicio first = exercicios.getFirst();
        Optional<Exercicio> byId = repository.findById(first.getId());
        Assertions.assertThat(byId).isPresent().contains(first);
    }

    @Test
    void saveSucess() {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        Exercicio build = Exercicio.builder().id(99L).name("gfgfghghg").build();
        Exercicio save = repository.save(build);
        Assertions.assertThat(save).isEqualTo(build).hasNoNullFieldsOrProperties();
        Optional<Exercicio> byId = repository.findById(save.getId());
        Assertions.assertThat(byId).isPresent().contains(build);
    }

    @Test
    void deleteSucess() {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        Exercicio first = exercicios.getFirst();
        Assertions.assertThatNoException().isThrownBy(() -> repository.delete(first));
        List<Exercicio> exercicios1 = repository.listALl();
        Assertions.assertThat(exercicios1).isNotEmpty().doesNotContain(first);
    }

    @Test
    void updateSucess() {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        Exercicio first = exercicios.getFirst();
        first.setName("fdffdfd");
        repository.update(first);
        Assertions.assertThat(exercicios).contains(first);
        Optional<Exercicio> byId = repository.findById(first.getId());
        Assertions.assertThat(byId).isPresent();
        Assertions.assertThat(byId.get().getName()).isEqualTo("fdffdfd");

    }
}
