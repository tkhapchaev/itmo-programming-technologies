package ru.tkhapchaev.services.user;

import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User save(User user);

    boolean deleteById(Long id);

    void deleteAll();

    boolean update(User user);

    User getById(Long id);

    List<User> getAll();

    List<User> getAllByRole(Long role);

    Optional<User> getByUsername(String username);
}