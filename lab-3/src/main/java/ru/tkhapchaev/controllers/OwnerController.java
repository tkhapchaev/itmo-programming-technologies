package ru.tkhapchaev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tkhapchaev.entities.Owner;
import ru.tkhapchaev.services.owner.OwnerService;

import java.util.List;

@RestController
public class OwnerController {
    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping(value = "/api/owners")
    public ResponseEntity<?> save(@RequestBody Owner owner) {
        ownerService.save(owner);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/api/owners/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") long id) {
        final boolean deleted = ownerService.deleteById(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/api/owners")
    public ResponseEntity<?> deleteByEntity(@RequestBody Owner owner) {
        final boolean deleted = ownerService.deleteByEntity(owner);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/api/owners/all")
    public ResponseEntity<?> deleteAll() {
        ownerService.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/api/owners/{id}")
    public ResponseEntity<?> update(@RequestBody Owner owner) {
        final boolean updated = ownerService.update(owner);

        return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/api/owners/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) {
        final Owner owner = ownerService.getById(id);

        return owner != null ? new ResponseEntity<>(owner, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/owners")
    public ResponseEntity<List<Owner>> getAll() {
        final List<Owner> owners = ownerService.getAll();

        return owners != null && !owners.isEmpty() ? new ResponseEntity<>(owners, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/owners/{name}")
    public ResponseEntity<List<Owner>> getAllByName(@PathVariable(name = "name") String name) {
        final List<Owner> owners = ownerService.getAllByName(name);

        return owners != null && !owners.isEmpty() ? new ResponseEntity<>(owners, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}