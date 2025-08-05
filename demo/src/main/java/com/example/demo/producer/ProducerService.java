package com.example.demo.producer;

import com.example.demo.domain.Producer;
import com.example.demo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProducerService {
    private final ProducerRepository repository;

    public List<Producer>findAll(String name){
        return name == null? repository.findAll(): repository.findByName(name);
    }
    public Producer findyByIdOrElseThrowNotFound(Long id){
      return repository.findById(id).orElseThrow(() -> new NotFoundException("Producer not found"));
    }
    public Producer save(Producer producer){
        return repository.save(producer);
    }
    public void delete(Long id){
        var producer = findyByIdOrElseThrowNotFound(id);
        repository.delete(producer);
    }
    public void update(Producer producerToUpdate){
        AssertProducerExists(producerToUpdate.getId());
        repository.save(producerToUpdate);
    }
    public void AssertProducerExists(Long id){
        findyByIdOrElseThrowNotFound(id);
    }
}
