package ru.tkhapchaev.services.role;

import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.Role;

import java.util.List;

@Service
public interface RoleService {
    Role save(Role role);

    boolean deleteById(Long id);

    void deleteAll();

    boolean update(Role role);

    Role getById(Long id);

    List<Role> getAll();
}