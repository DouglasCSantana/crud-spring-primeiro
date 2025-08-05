package com.example.demo.producer;

import com.example.demo.commons.FilesUtils;
import com.example.demo.commons.ProducerUtils;
import com.example.demo.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"com.example.demo.producer", "com.example.demo.commons"})
//@Import({ProducerMapperImpl.class, ProducerService.class, ProducerHardCodedRepository.class, ProducerData.class})
//@ActiveProfiles("teste")
class ProducerControllerTest {
    private static final String URL = "/v1/producers";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProducerRepository repository;
    private List<Producer> producersList;
    @Autowired
    private FilesUtils filesUtils;
    @Autowired
    private ProducerUtils producerUtils;

    @BeforeEach
    void init() {
        producersList = producerUtils.newProducerList();
    }

    @Test
    @DisplayName("Get v1/producers returns a list with all producer when name is null")
    @Order(1)
    void findAllNull_AllProducers_WhenArgumentIsNull() throws Exception {
        BDDMockito.when(repository.findAll()).thenReturn(producersList);
        var response = filesUtils.readResouserfile("producer/get-producer-null-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("Get v1/producers?name=mappa returns list with found object when name exists")
    @Order(2)
    void findAll_ReturnsEmptyList_WhenNameIsNotfound() throws Exception {
        var response = filesUtils.readResouserfile("producer/get-producer-mappa-name-200.json");
        var name = "mappa";
        var mappa = producersList.stream().filter(producer -> producer.getName().equals(name)).findFirst().orElse(null);
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.singletonList(mappa));
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("Get v1/producers?name=x returns empty list when name is not found")
    @Order(3)
    void findAll_ReturnsProducerByName_WhenSuccessful() throws Exception {
        var response = filesUtils.readResouserfile("producer/get-producer-x-name-200.json");
        var name = "x";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("Get v1/producers/1 returns a producer with given id")
    @Order(4)
    void findById_ReturnsProducerById_WhenSuccessful() throws Exception {
        var response = filesUtils.readResouserfile("producer/get-producer-by-id-200.json");
        var id = 1L;
        var foundProducer = producersList.stream().filter(producer -> producer.getId().equals(id)).findFirst();
        BDDMockito.when(repository.findById(id)).thenReturn(foundProducer);
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("Get v1/producers/99 throws ResponseStatusException 404 when producer is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenProducerIsNotfound() throws Exception {
        var response = filesUtils.readResouserfile("producer/get-producer-by-id-404.json");
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("POST v1/producers creates a producer")
    @Order(6)
    void save_createsProducer_WhenSuccessful() throws Exception {
        var request = filesUtils.readResouserfile("producer/post-request-producer-200.json");
        var response = filesUtils.readResouserfile("producer/post-response-producer-201.json");
        Producer producerToSave = producerUtils.newProducerToSave();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(producerToSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .header("x-api-key", "1234")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("PUT v1/producer updates a producer")
    @Order(7)
    void update_UpdatesProducer_WhenSuccessful() throws Exception {
        var request = filesUtils.readResouserfile("producer/put-request-producer-200.json");
        var id = 1L;
        var foundProducer = producersList.stream().filter(producer -> producer.getId().equals(id)).findFirst();
        BDDMockito.when(repository.findById(id)).thenReturn(foundProducer);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("PUT v1/producer throws ResponseStatusException when producer is not found")
    @Order(8)
    void update_ThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {
        var request = filesUtils.readResouserfile("producer/put-request-producer-404.json");
        var response = filesUtils.readResouserfile("producer/update-producer-by-id-404.json");
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }


    @Test
    @DisplayName("DELETE v1/producer/1 removes a producer")
    @Order(9)
    void delete_RemoveProducer_WhenSuccessful() throws Exception {
        var id = producersList.getFirst().getId();
        var foundProducer = producersList.stream().filter(producer -> producer.getId().equals(id)).findFirst();
        BDDMockito.when(repository.findById(id)).thenReturn(foundProducer);
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE v1/producer/99 throws ResponseStatusException when producer is not found")
    @Order(10)
    void delete_ThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {
        var response = filesUtils.readResouserfile("producer/delete-producer-by-id-404.json");
        var id = 99;
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }
    @ParameterizedTest
    @MethodSource("putProducerBadRequestSource")
    @DisplayName("PUT v1/producer returns bad request when fields are invalid")
    @Order(11)
    void update_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String>errors) throws Exception {
        BDDMockito.when(repository.findAll()).thenReturn(producersList);
        var request = filesUtils.readResouserfile("producer/%s".formatted(fileName));
        var mvcResult= mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        var resolvedException = mvcResult.getResolvedException();
        Assertions.assertThat(resolvedException).isNotNull();
        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    @ParameterizedTest
    @MethodSource("postUserProducerRequestSource")
    @DisplayName("POST v1/producers rgit eturns bad request when fields are invalid")
    @Order(12)
    void save_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors) throws Exception {
        var request = filesUtils.readResouserfile("producer/%s".formatted(fileName));
        Producer producerToSave = producerUtils.newProducerToSave();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(producerToSave);
        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .header("x-api-key", "1234")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();
        Assertions.assertThat(resolvedException).isNotNull();
        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    private static List<String> allRequiredErrors() {
        var nameRequiredError = "The field 'name' is required";
        return new ArrayList<>(List.of(nameRequiredError));
    }

    private static Stream<Arguments> putProducerBadRequestSource() {
        var allRequiredErrors = allRequiredErrors();
        allRequiredErrors.add("The field 'id' cannot be null");
        return Stream.of(
                Arguments.of("put-request-blank-fields-400.json",allRequiredErrors),
                Arguments.of("put-request-empty-fields-400.json",allRequiredErrors)
        );
    }
    private static Stream<Arguments> postUserProducerRequestSource() {
        var allRequiredErrors = allRequiredErrors();
        return Stream.of(
                Arguments.of("post-request-blank-fields-400.json",allRequiredErrors),
                Arguments.of("post-request-empty-fields-400.json",allRequiredErrors)
        );
    }
}