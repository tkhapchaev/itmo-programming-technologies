package ru.tkhapchaev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tkhapchaev.entities.Cat;
import ru.tkhapchaev.services.cat.CatService;

import java.util.List;

@RestController
public class CatController {
    private final CatService catService;

    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @PostMapping(value = "/api/cats")
    public ResponseEntity<?> save(@RequestBody Cat cat) {
        catService.save(cat);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/api/cats/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") long id) {
        final boolean deleted = catService.deleteById(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/api/cats")
    public ResponseEntity<?> deleteByEntity(@RequestBody Cat cat) {
        final boolean deleted = catService.deleteByEntity(cat);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/api/cats/all")
    public ResponseEntity<?> deleteAll() {
        catService.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/api/cats/{id}")
    public ResponseEntity<?> update(@RequestBody Cat cat) {
        final boolean updated = catService.update(cat);

        return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/api/cats/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) {
        final Cat cat = catService.getById(id);

        return cat != null ? new ResponseEntity<>(cat, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/cats")
    public ResponseEntity<List<Cat>> getAll() {
        final List<Cat> cats = catService.getAll();

        return cats != null && !cats.isEmpty() ? new ResponseEntity<>(cats, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/cats/owner/{id}")
    public ResponseEntity<List<Cat>> getAllByOwnerId(@PathVariable(name = "id") long id) {
        final List<Cat> cats = catService.getAllByOwnerId(id);

        return cats != null && !cats.isEmpty() ? new ResponseEntity<>(cats, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/cats/{name}")
    public ResponseEntity<List<Cat>> getAllByName(@PathVariable(name = "name") String name) {
        final List<Cat> cats = catService.getAllByName(name);

        return cats != null && !cats.isEmpty() ? new ResponseEntity<>(cats, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}