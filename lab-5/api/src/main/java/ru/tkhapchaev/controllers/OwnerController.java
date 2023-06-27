package ru.tkhapchaev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tkhapchaev.models.OwnerDto;
import ru.tkhapchaev.models.message.OwnerMessage;
import ru.tkhapchaev.services.rabbit.producer.RabbitProducerService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/owners")
public class OwnerController {
    private final RabbitProducerService rabbitProducerService;

    @Autowired
    public OwnerController(RabbitProducerService rabbitProducerService) {
        this.rabbitProducerService = rabbitProducerService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OwnerDto ownerDto) {
        List<OwnerDto> ownerDtos = new ArrayList<>();
        ownerDtos.add(ownerDto);
        rabbitProducerService.sendOwners(new OwnerMessage(ownerDto.getId(), "create", ownerDtos));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") long id) {
        List<OwnerDto> ownerDtos = new ArrayList<>();
        ownerDtos.add(null);
        rabbitProducerService.sendOwners(new OwnerMessage(id, "delete", ownerDtos));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByEntity(@RequestBody OwnerDto ownerDto) {
        List<OwnerDto> ownerDtos = new ArrayList<>();
        ownerDtos.add(ownerDto);
        rabbitProducerService.sendOwners(new OwnerMessage(ownerDto.getId(), "delete", ownerDtos));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/all")
    public ResponseEntity<?> deleteAll() {
        List<OwnerDto> ownerDtos = new ArrayList<>();
        ownerDtos.add(null);
        rabbitProducerService.sendOwners(new OwnerMessage(null, "deleteAll", ownerDtos));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody OwnerDto ownerDto) {
        List<OwnerDto> ownerDtos = new ArrayList<>();
        ownerDtos.add(ownerDto);
        rabbitProducerService.sendOwners(new OwnerMessage(ownerDto.getId(), "update", ownerDtos));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) {
        List<OwnerDto> ownerDtos = new ArrayList<>();
        ownerDtos.add(null);
        OwnerMessage message = rabbitProducerService.sendOwners(new OwnerMessage(id, "get", ownerDtos));

        return new ResponseEntity<>(message.getOwners(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAll() {
        List<OwnerDto> ownerDtos = new ArrayList<>();
        ownerDtos.add(null);
        OwnerMessage message = rabbitProducerService.sendOwners(new OwnerMessage(null, "getAll", ownerDtos));

        return new ResponseEntity<>(message.getOwners(), HttpStatus.OK);
    }
}