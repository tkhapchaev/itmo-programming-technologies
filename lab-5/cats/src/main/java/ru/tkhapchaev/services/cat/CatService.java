package ru.tkhapchaev.services.cat;

import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.Cat;

import java.util.List;

@Service
public interface CatService {
    Cat save(Cat cat);

    boolean deleteById(Long id);

    boolean deleteByEntity(Cat cat);

    void deleteAll();

    boolean update(Cat cat);

    Cat getById(Long id);

    List<Cat> getAll();

    List<Cat> getAllByOwnerId(Long id);
}