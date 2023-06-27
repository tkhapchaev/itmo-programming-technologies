package ru.tkhapchaev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tkhapchaev.entities.Cat;
import ru.tkhapchaev.models.CatDto;
import ru.tkhapchaev.services.cat.CatService;
import ru.tkhapchaev.services.owner.OwnerService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/cats")
public class CatController {
    private final CatService catService;

    private final OwnerService ownerService;

    @Autowired
    public CatController(CatService catService, OwnerService ownerService) {
        this.catService = catService;
        this.ownerService = ownerService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CatDto catDto) {
        catService.save(new Cat(catDto, ownerService.getById(catDto.getOwner())));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") long id) {
        final boolean deleted = catService.deleteById(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByEntity(@RequestBody CatDto catDto) {
        final boolean deleted = catService.deleteByEntity(new Cat(catDto, ownerService.getById(catDto.getOwner())));

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/all")
    public ResponseEntity<?> deleteAll() {
        catService.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody CatDto catDto) {
        final boolean updated = catService.update(new Cat(catDto, ownerService.getById(catDto.getOwner())));

        return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) {
        final Cat cat = catService.getById(id);

        return cat != null ? new ResponseEntity<>(cat.asDto(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<CatDto>> getAll() {
        final List<Cat> cats = catService.getAll();

        if (cats == null || cats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CatDto> catDtos = new ArrayList<>();

        for (Cat cat : cats) {
            catDtos.add(cat.asDto());
        }

        return new ResponseEntity<>(catDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/owner/{id}")
    public ResponseEntity<List<CatDto>> getAllByOwnerId(@PathVariable(name = "id") long id) {
        final List<Cat> cats = catService.getAllByOwnerId(id);

        if (cats == null || cats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CatDto> catDtos = new ArrayList<>();

        for (Cat cat : cats) {
            catDtos.add(cat.asDto());
        }

        return new ResponseEntity<>(catDtos, HttpStatus.OK);
    }
}