package ru.tkhapchaev.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tkhapchaev.entities.User;
import ru.tkhapchaev.models.UserDto;
import ru.tkhapchaev.repositories.OwnerRepository;
import ru.tkhapchaev.services.role.RoleService;
import ru.tkhapchaev.services.user.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    private final UserService userService;

    private final RoleService roleService;

    private final OwnerRepository ownerRepository;

    public UserController(UserService userService, RoleService roleService, OwnerRepository ownerRepository) {
        this.userService = userService;
        this.roleService = roleService;
        this.ownerRepository = ownerRepository;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        userService.save(new User(userDto, roleService.getById(userDto.getRole()), ownerRepository.findById(userDto.getOwner()).orElse(null)));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") long id) {
        final boolean deleted = userService.deleteById(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/all")
    public ResponseEntity<?> deleteAll() {
        userService.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody UserDto userDto) {
        final boolean updated = userService.update(new User(userDto, roleService.getById(userDto.getRole()), ownerRepository.findById(userDto.getOwner()).orElse(null)));

        return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) {
        final User user = userService.getById(id);

        return user != null ? new ResponseEntity<>(user.asDto(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        final List<User> users = userService.getAll();

        if (users == null || users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(user.asDto());
        }

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
}