package ru.tkhapchaev.services.owner;

import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.Owner;

import java.util.List;

@Service
public interface OwnerService {
    Owner save(Owner owner);

    boolean deleteById(Long id);

    boolean deleteByEntity(Owner owner);

    void deleteAll();

    boolean update(Owner owner);

    Owner getById(Long id);

    List<Owner> getAll();

    List<Owner> getAllByName(String name);
}