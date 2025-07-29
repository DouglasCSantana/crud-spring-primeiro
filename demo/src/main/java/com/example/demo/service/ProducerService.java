package com.example.demo.service;

import com.example.demo.domain.Producer;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ProducerHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProducerService {
    private final ProducerHardCodedRepository repository;

    public List<Producer>findAll(String name){
        return name == null? repository.findyAll(): repository.findyByName(name);
    }
    public Producer findyByIdOrElseThrow(Long id){
      return repository.findyById(id).orElseThrow(() -> new NotFoundException("Producer not found"));
    }
    public Producer save(Producer producer){
        return repository.save(producer);
    }
    public void delete(Long id){
        var producer = findyByIdOrElseThrow(id);
        repository.delete(producer);
    }
    public void update(Producer producerToUpdate){
        var producer = findyByIdOrElseThrow(producerToUpdate.getId());
        producerToUpdate.setLocalDateTime(producer.getLocalDateTime());
        repository.update(producerToUpdate);
    }
}
