package ru.tkhapchaev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tkhapchaev.entities.Flea;
import ru.tkhapchaev.services.flea.FleaService;

import java.util.List;

@RestController
public class FleaController {
    private final FleaService fleaService;

    @Autowired
    public FleaController(FleaService fleaService) {
        this.fleaService = fleaService;
    }

    @PostMapping(value = "/api/fleas")
    public ResponseEntity<?> save(@RequestBody Flea flea) {
        fleaService.save(flea);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/api/fleas/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") long id) {
        final boolean deleted = fleaService.deleteById(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/api/fleas")
    public ResponseEntity<?> deleteByEntity(@RequestBody Flea flea) {
        final boolean deleted = fleaService.deleteByEntity(flea);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/api/fleas/all")
    public ResponseEntity<?> deleteAll() {
        fleaService.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/api/fleas/{id}")
    public ResponseEntity<?> update(@RequestBody Flea flea) {
        final boolean updated = fleaService.update(flea);

        return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/api/fleas/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) {
        final Flea flea = fleaService.getById(id);

        return flea != null ? new ResponseEntity<>(flea, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/fleas")
    public ResponseEntity<List<Flea>> getAll() {
        final List<Flea> fleas = fleaService.getAll();

        return fleas != null && !fleas.isEmpty() ? new ResponseEntity<>(fleas, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/fleas/cat/{id}")
    public ResponseEntity<List<Flea>> getAllByOwnerId(@PathVariable(name = "id") long id) {
        final List<Flea> fleas = fleaService.getAllByCatId(id);

        return fleas != null && !fleas.isEmpty() ? new ResponseEntity<>(fleas, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}