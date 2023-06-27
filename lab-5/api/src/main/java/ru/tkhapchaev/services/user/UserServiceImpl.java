package ru.tkhapchaev.services.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.User;
import ru.tkhapchaev.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        userRepository.save(user);

        return user;
    }

    @Override
    public boolean deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);

            return true;
        }

        return false;
    }

    @Override
    public void deleteAll() {
        List<User> users = getAll();

        for (User user : users) {
            deleteById(user.getId());
        }
    }

    @Override
    public boolean update(User user) {
        if (userRepository.existsById(user.getId())) {
            userRepository.deleteById(user.getId());
            userRepository.save(user);

            return true;
        }

        return false;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllByRole(Long role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}