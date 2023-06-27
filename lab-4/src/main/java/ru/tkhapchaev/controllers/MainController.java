package ru.tkhapchaev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tkhapchaev.entities.Cat;
import ru.tkhapchaev.entities.Flea;
import ru.tkhapchaev.entities.User;
import ru.tkhapchaev.models.CatDto;
import ru.tkhapchaev.models.FleaDto;
import ru.tkhapchaev.services.cat.CatService;
import ru.tkhapchaev.services.flea.FleaService;
import ru.tkhapchaev.services.user.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class MainController {
    private final UserService userService;

    private final CatService catService;

    private final FleaService fleaService;

    @Autowired
    public MainController(UserService userService, CatService catService, FleaService fleaService) {
        this.userService = userService;
        this.catService = catService;
        this.fleaService = fleaService;
    }

    @GetMapping(value = "/cats")
    public ResponseEntity<List<CatDto>> getAllCats(Principal principal) {
        Optional<User> user = userService.getByUsername(principal.getName());

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final List<Cat> cats = catService.getAllByOwnerId(user.get().getOwner().getId());

        if (cats == null || cats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CatDto> catDtos = new ArrayList<>();

        for (Cat cat : cats) {
            catDtos.add(cat.asDto());
        }

        return new ResponseEntity<>(catDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/fleas")
    public ResponseEntity<List<FleaDto>> getAllFleas(Principal principal) {
        Optional<User> user = userService.getByUsername(principal.getName());

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final List<Cat> cats = catService.getAllByOwnerId(user.get().getOwner().getId());

        if (cats == null || cats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final List<Flea> fleas = new ArrayList<>();

        for (Cat cat : cats) {
            List<Flea> currentFleas = fleaService.getAllByCatId(cat.getId());
            fleas.addAll(currentFleas);
        }

        if (fleas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<FleaDto> fleaDtos = new ArrayList<>();

        for (Flea flea : fleas) {
            fleaDtos.add(flea.asDto());
        }

        return new ResponseEntity<>(fleaDtos, HttpStatus.OK);
    }
}