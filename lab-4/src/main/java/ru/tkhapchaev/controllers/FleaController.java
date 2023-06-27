package ru.tkhapchaev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tkhapchaev.entities.Cat;
import ru.tkhapchaev.entities.Flea;
import ru.tkhapchaev.models.CatDto;
import ru.tkhapchaev.models.FleaDto;
import ru.tkhapchaev.services.cat.CatService;
import ru.tkhapchaev.services.flea.FleaService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/fleas")
public class FleaController {
    private final FleaService fleaService;

    private final CatService catService;

    @Autowired
    public FleaController(FleaService fleaService, CatService catService) {
        this.fleaService = fleaService;
        this.catService = catService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody FleaDto fleaDto) {
        fleaService.save(new Flea(fleaDto, catService.getById(fleaDto.getCat())));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") long id) {
        final boolean deleted = fleaService.deleteById(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByEntity(@RequestBody FleaDto fleaDto) {
        final boolean deleted = fleaService.deleteByEntity(new Flea(fleaDto, catService.getById(fleaDto.getCat())));

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/all")
    public ResponseEntity<?> deleteAll() {
        fleaService.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody FleaDto fleaDto) {
        final boolean updated = fleaService.update(new Flea(fleaDto, catService.getById(fleaDto.getCat())));

        return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) {
        final Flea flea = fleaService.getById(id);

        return flea != null ? new ResponseEntity<>(flea.asDto(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<FleaDto>> getAll() {
        final List<Flea> fleas = fleaService.getAll();

        if (fleas == null || fleas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<FleaDto> fleaDtos = new ArrayList<>();

        for (Flea flea : fleas) {
            fleaDtos.add(flea.asDto());
        }

        return new ResponseEntity<>(fleaDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/cat/{id}")
    public ResponseEntity<List<FleaDto>> getAllByOwnerId(@PathVariable(name = "id") long id) {
        final List<Flea> fleas = fleaService.getAllByCatId(id);

        if (fleas == null || fleas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<FleaDto> fleaDtos = new ArrayList<>();

        for (Flea flea : fleas) {
            fleaDtos.add(flea.asDto());
        }

        return new ResponseEntity<>(fleaDtos, HttpStatus.OK);
    }
}