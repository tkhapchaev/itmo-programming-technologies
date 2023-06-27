package ru.tkhapchaev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tkhapchaev.models.CatDto;
import ru.tkhapchaev.models.message.CatMessage;
import ru.tkhapchaev.services.rabbit.producer.RabbitProducerService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/cats")
public class CatController {
    private final RabbitProducerService rabbitProducerService;

    @Autowired
    public CatController(RabbitProducerService rabbitProducerService) {
        this.rabbitProducerService = rabbitProducerService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CatDto catDto) {
        List<CatDto> catDtos = new ArrayList<>();
        catDtos.add(catDto);
        rabbitProducerService.sendCats(new CatMessage(catDto.getId(), "create", catDtos));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") long id) {
        List<CatDto> catDtos = new ArrayList<>();
        catDtos.add(null);
        rabbitProducerService.sendCats(new CatMessage(id, "delete", catDtos));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByEntity(@RequestBody CatDto catDto) {
        List<CatDto> catDtos = new ArrayList<>();
        catDtos.add(catDto);
        rabbitProducerService.sendCats(new CatMessage(catDto.getId(), "delete", catDtos));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/all")
    public ResponseEntity<?> deleteAll() {
        List<CatDto> catDtos = new ArrayList<>();
        catDtos.add(null);
        rabbitProducerService.sendCats(new CatMessage(null, "deleteAll", catDtos));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody CatDto catDto) {
        List<CatDto> catDtos = new ArrayList<>();
        catDtos.add(catDto);
        rabbitProducerService.sendCats(new CatMessage(catDto.getId(), "update", catDtos));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) {
        List<CatDto> catDtos = new ArrayList<>();
        catDtos.add(null);
        CatMessage message = rabbitProducerService.sendCats(new CatMessage(id, "get", catDtos));

        return new ResponseEntity<>(message.getCats(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CatDto>> getAll() {
        List<CatDto> catDtos = new ArrayList<>();
        catDtos.add(null);
        CatMessage message = rabbitProducerService.sendCats(new CatMessage(null, "getAll", catDtos));

        return new ResponseEntity<>(message.getCats(), HttpStatus.OK);
    }

    @GetMapping(value = "/owner/{id}")
    public ResponseEntity<List<CatDto>> getAllByOwnerId(@PathVariable(name = "id") long id) {
        List<CatDto> catDtos = new ArrayList<>();
        catDtos.add(null);
        CatMessage message = rabbitProducerService.sendCats(new CatMessage(id, "getAllCatsByOwnerId", catDtos));

        return new ResponseEntity<>(message.getCats(), HttpStatus.OK);
    }
}