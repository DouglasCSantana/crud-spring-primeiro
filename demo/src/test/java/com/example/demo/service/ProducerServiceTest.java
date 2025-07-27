package com.example.demo.service;

import com.example.demo.commons.ProducerUtils;
import com.example.demo.domain.Producer;
import com.example.demo.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ProducerServiceTest {
    @InjectMocks
    private ProducerService service;
    @Mock
    private ProducerHardCodedRepository repository;
    private List<Producer> producerList;
    @InjectMocks
    private ProducerUtils producerUtils;
    @BeforeEach
    void init(){
        producerList = producerUtils.newProducerList();
    }
    @Test
    @DisplayName("findAll returns a list with all producer when name is null")
    @Order(1)
    void findAllNull_AllProducers_WhenArgumentIsNull(){
        BDDMockito.when(repository.findyAll()).thenReturn(producerList);
        var producers = service.findAll(null);
        Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producerList);
    }
    @Test
    @DisplayName("findAll returns list with found object when name exists")
    @Order(2)
    void findByName_ReturnsfoundProducerInList_WhenNameIsfound(){
        var producer = producerList.getFirst();
        var expectedProducerfound = singletonList(producer);
        BDDMockito.when(repository.findyByName(producer.getName())).thenReturn(expectedProducerfound);
        var producersfound = service.findAll(producer.getName());
        Assertions.assertThat(producersfound).containsAll(expectedProducerfound);
    }
    @Test
    @DisplayName("findAll returns empty list when name is not found")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNotfound(){
        var name = "not-found";
        BDDMockito.when(repository.findyByName(name)).thenReturn(emptyList());
        var producers = service.findAll(name);
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }
    @Test
    @DisplayName("findById returns a producer with given id")
    @Order(4)
    void findById_ReturnsProducerById_WhenSuccessful(){
        var expectedProducer = producerList.getFirst();
        BDDMockito.when(repository.findyById(expectedProducer.getId())).thenReturn(Optional.of(expectedProducer));
        var producers = service.findyByIdOrElseThrow(expectedProducer.getId());
        Assertions.assertThat(producers).isEqualTo(expectedProducer);
    }
    @Test
    @DisplayName("findById throws ResponseStatusException when producer is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenProducerIsNotfound(){
        var expectedProducer = producerList.getFirst();
        BDDMockito.when(repository.findyById(expectedProducer.getId())).thenReturn(Optional.empty());
        Assertions.assertThatException()
                .isThrownBy(() -> service.findyByIdOrElseThrow(expectedProducer.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }
    @Test
    @DisplayName("save creates a producer")
    @Order(6)
    void save_createsProducer_WhenSuccessful(){
        var producerToSave = producerUtils.newProducerToSave();
        BDDMockito.when(repository.save(producerToSave)).thenReturn(producerToSave);
        var savedProducer = service.save(producerToSave);
        Assertions.assertThat(savedProducer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();
    }
    @Test
    @DisplayName("delete removes a producer")
    @Order(7)
    void delete_RemoveProducer_WhenSuccessful(){
        var producertoDelete = producerList.getFirst();
        BDDMockito.when(repository.findyById(producertoDelete.getId())).thenReturn(Optional.of(producertoDelete));
        BDDMockito.doNothing().when(repository).delete(producertoDelete);
        Assertions.assertThatNoException().isThrownBy(() -> service.delete(producertoDelete.getId()));
    }
    @Test
    @DisplayName("delete throws ResponseStatusException when producer is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenProducerIsNotFound(){
        var producertoDelete = producerList.getFirst();
        BDDMockito.when(repository.findyById(producertoDelete.getId())).thenReturn(Optional.empty());
        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(producertoDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }
    @Test
    @DisplayName("update updates a producer")
    @Order(9)
    void update_UpdatesProducer_WhenSuccessful(){
        var producertoUpdate = producerList.getFirst();
        producertoUpdate.setName("aniplex");
        BDDMockito.when(repository.findyById(producertoUpdate.getId())).thenReturn(Optional.of(producertoUpdate));
        BDDMockito.doNothing().when(repository).update(producertoUpdate);
        Assertions.assertThatNoException().isThrownBy(() -> service.update(producertoUpdate));
    }
    @Test
    @DisplayName("update throws ResponseStatusException when producer is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenProducerIsNotFound(){
        var producertoUpdate = producerList.getFirst();
        BDDMockito.when(repository.findyById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThatException()
                .isThrownBy(() -> service.update(producertoUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }

}