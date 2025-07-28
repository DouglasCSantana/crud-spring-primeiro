package com.example.demo.controller;

import com.example.demo.domain.Exercicio;
import com.example.demo.repository.ExecicioHardRepository;
import com.example.demo.repository.ExercicioData;
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
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(controllers = ExercicioController.class)
@ComponentScan(basePackages = "com.example.demo")
class ExercicioControllerTest {
    @Autowired
    private MockMvc mvcTest;
    @MockBean
    private ExercicioData exercicioData;
    @SpyBean
    private ExecicioHardRepository execicioHardRepository;
    private List<Exercicio> exercicios;
    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    void init() {
        Exercicio build = Exercicio.builder().id(1L).name("asdas").build();
        Exercicio build1 = Exercicio.builder().id(2L).name("fgfg").build();
        exercicios = new ArrayList<>(List.of(build1, build));
    }

    @Test
    void findByAllSucess() throws Exception {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        String resoul = resoul("Exercicio/get-exercicio-null-200.json");
        mvcTest.perform(MockMvcRequestBuilders.get("/v1/exercicios"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resoul));
    }

    @Test
    void findByNameSucess() throws Exception {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        String resoul = resoul("Exercicio/get-exercicio-sdhgds-200.json");
        var name = "asdas";
        mvcTest.perform(MockMvcRequestBuilders.get("/v1/exercicios").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resoul));
    }

    @Test
    void findByNameNotSucess() throws Exception {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        String resoul = resoul("Exercicio/get-exercicio-x-name-200.json");
        var name = "x";
        mvcTest.perform(MockMvcRequestBuilders.get("/v1/exercicios").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resoul));
    }

    @Test
    void findByIdSucess() throws Exception {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        String resoul = resoul("Exercicio/get-exercicio-by-id-200.json");
        var id = 1L;
        mvcTest.perform(MockMvcRequestBuilders.get("/v1/exercicios/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resoul));
    }

    @Test
    void findByIdNotSucess() throws Exception {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        var id = 99L;
        mvcTest.perform(MockMvcRequestBuilders.get("/v1/exercicios/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteSucess() throws Exception {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        var id = 1L;
        mvcTest.perform(MockMvcRequestBuilders.delete("/v1/exercicios/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteNotSucess() throws Exception {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        var id = 99L;
        mvcTest.perform(MockMvcRequestBuilders.delete("/v1/exercicios/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateSucess() throws Exception {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        String resoul = resoul("Exercicio/put-request-exercicio-200.json");
        mvcTest.perform(MockMvcRequestBuilders.put("/v1/exercicios").content(resoul).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void updateNotSucess() throws Exception {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        String resoul = resoul("Exercicio/put-request-exercicio-404.json");
        mvcTest.perform(MockMvcRequestBuilders.put("/v1/exercicios").content(resoul).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void saveSucess() throws Exception {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        String resoul = resoul("Exercicio/post-request-exercicio-200.json");
        String resoul1 = resoul("Exercicio/post-response-exercicio-201.json");
        Exercicio build = Exercicio.builder().id(99L).name("dfdfssdf").build();
        BDDMockito.when(execicioHardRepository.save(ArgumentMatchers.any())).thenReturn(build);
        mvcTest.perform(MockMvcRequestBuilders.post("/v1/exercicios").content(resoul).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(resoul1));
    }

    @ParameterizedTest
    @MethodSource("saveInvalidation")
    void saveBadRequest(String fileName, List<String> errors) throws Exception {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        String resoul = resoul("Exercicio/%s".formatted(fileName));
        var mvcResult = mvcTest.perform(MockMvcRequestBuilders
                        .post("/v1/exercicios")
                        .content(resoul)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        var resolvedException = mvcResult.getResolvedException();
        Assertions.assertThat(resolvedException).isNotNull();
        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

   @ParameterizedTest
   @MethodSource("putInvalidation")
        void putBadRequest(String fileName, List<String> errors) throws Exception {
        BDDMockito.when(exercicioData.lista()).thenReturn(exercicios);
        String resoul = resoul("Exercicio/%s".formatted(fileName));
        var mvcResult = mvcTest.perform(MockMvcRequestBuilders.put("/v1/exercicios")
                        .content(resoul)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();
        Assertions.assertThat(resolvedException).isNotNull();
        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }
    private static Stream<Arguments>putInvalidation() {
        var allRequiredErrors = allRequiredErrors();
        allRequiredErrors.add("The field 'id' cannot be null");
        return Stream.of(
                Arguments.of("put-request-blank-fields-400.json",allRequiredErrors),
                Arguments.of("put-request-empty-fields-400.json",allRequiredErrors)
        );
    }

    private static Stream<Arguments> saveInvalidation() {
        var allRequiredErrors = allRequiredErrors();
        return Stream.of(
                Arguments.of("post-request-blank-fields-400.json", allRequiredErrors),
                Arguments.of("post-request-empy-400.json", allRequiredErrors)
        );
    }

    private static List<String> allRequiredErrors() {
        var nameInvalid = "The field 'name' is required";
        return new ArrayList<>(List.of(nameInvalid));
    }

    private String resoul(String fileName) throws IOException {
        File file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));

    }
}



