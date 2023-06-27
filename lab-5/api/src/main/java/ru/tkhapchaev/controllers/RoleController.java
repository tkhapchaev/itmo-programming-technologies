package ru.tkhapchaev.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.tkhapchaev.entities.Role;
import ru.tkhapchaev.models.RoleDto;
import ru.tkhapchaev.services.role.RoleService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody RoleDto roleDto) {
        roleService.save(new Role(roleDto));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") long id) {
        final boolean deleted = roleService.deleteById(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/all")
    public ResponseEntity<?> deleteAll() {
        roleService.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody RoleDto roleDto) {
        final boolean updated = roleService.update(new Role(roleDto));

        return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) {
        final Role role = roleService.getById(id);

        return role != null ? new ResponseEntity<>(role.asDto(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAll() {
        final List<Role> roles = roleService.getAll();

        if (roles == null || roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<RoleDto> roleDtos = new ArrayList<>();

        for (Role role : roles) {
            roleDtos.add(role.asDto());
        }

        return new ResponseEntity<>(roleDtos, HttpStatus.OK);
    }
}