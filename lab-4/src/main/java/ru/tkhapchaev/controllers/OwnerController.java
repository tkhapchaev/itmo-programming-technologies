package ru.tkhapchaev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tkhapchaev.entities.Flea;
import ru.tkhapchaev.entities.Owner;
import ru.tkhapchaev.models.FleaDto;
import ru.tkhapchaev.models.OwnerDto;
import ru.tkhapchaev.services.owner.OwnerService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/owners")
public class OwnerController {
    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OwnerDto ownerDto) {
        ownerService.save(new Owner(ownerDto));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") long id) {
        final boolean deleted = ownerService.deleteById(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByEntity(@RequestBody OwnerDto ownerDto) {
        final boolean deleted = ownerService.deleteByEntity(new Owner(ownerDto));

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/all")
    public ResponseEntity<?> deleteAll() {
        ownerService.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody OwnerDto ownerDto) {
        final boolean updated = ownerService.update(new Owner(ownerDto));

        return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) {
        final Owner owner = ownerService.getById(id);

        return owner != null ? new ResponseEntity<>(owner.asDto(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAll() {
        final List<Owner> owners = ownerService.getAll();

        if (owners == null || owners.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<OwnerDto> ownerDtos = new ArrayList<>();

        for (Owner owner : owners) {
            ownerDtos.add(owner.asDto());
        }

        return new ResponseEntity<>(ownerDtos, HttpStatus.OK);
    }
}