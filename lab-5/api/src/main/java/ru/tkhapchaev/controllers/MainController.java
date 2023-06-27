package ru.tkhapchaev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tkhapchaev.entities.User;
import ru.tkhapchaev.models.CatDto;
import ru.tkhapchaev.models.message.CatMessage;
import ru.tkhapchaev.services.rabbit.producer.RabbitProducerService;
import ru.tkhapchaev.services.user.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cats")
public class MainController {
    private final UserService userService;

    private final RabbitProducerService rabbitProducerService;

    @Autowired
    public MainController(UserService userService, RabbitProducerService rabbitProducerService) {
        this.userService = userService;
        this.rabbitProducerService = rabbitProducerService;
    }

    @GetMapping
    public ResponseEntity<List<CatDto>> getCats(Principal principal) {
        Optional<User> user = userService.getByUsername(principal.getName());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }

        CatMessage message = rabbitProducerService.sendCats(new CatMessage(user.get().getOwner().getId(), "getAllCatsByOwnerId", null));

        return new ResponseEntity<>(message.getCats(), HttpStatus.OK);
    }
}