package ru.tkhapchaev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkhapchaev.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}