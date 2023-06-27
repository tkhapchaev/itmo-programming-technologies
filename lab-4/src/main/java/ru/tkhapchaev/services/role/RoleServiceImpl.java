package ru.tkhapchaev.services.role;

import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.Role;
import ru.tkhapchaev.entities.User;
import ru.tkhapchaev.repositories.RoleRepository;
import ru.tkhapchaev.services.user.UserService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    private final UserService userService;

    public RoleServiceImpl(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public Role save(Role role) {
        roleRepository.save(role);

        return role;
    }

    @Override
    public boolean deleteById(Long id) {
        if (roleRepository.existsById(id)) {
            List<User> users = userService.getAllByRole(id);

            for (User user : users) {
                userService.deleteById(user.getId());
            }

            roleRepository.deleteById(id);

            return true;
        }

        return false;
    }

    @Override
    public void deleteAll() {
        List<Role> roles = getAll();

        for (Role role : roles) {
            deleteById(role.getId());
        }
    }

    @Override
    public boolean update(Role role) {
        if (roleRepository.existsById(role.getId())) {
            roleRepository.deleteById(role.getId());
            roleRepository.save(role);

            return true;
        }

        return false;
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}