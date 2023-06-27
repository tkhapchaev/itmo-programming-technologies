package ru.tkhapchaev.services.flea;

import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.Flea;

import java.util.List;

@Service
public interface FleaService {
    Flea save(Flea flea);

    boolean deleteById(Long id);

    boolean deleteByEntity(Flea flea);

    void deleteAll();

    boolean update(Flea flea);

    Flea getById(Long id);

    List<Flea> getAll();

    List<Flea> getAllByCatId(Long id);
}