package ru.tkhapchaev.services.cat;

import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.Cat;
import ru.tkhapchaev.repositories.CatRepository;

import java.util.List;

@Service
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;

    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    public Cat save(Cat cat) {
        catRepository.save(cat);

        return cat;
    }

    @Override
    public boolean deleteById(Long id) {
        if (catRepository.existsById(id)) {
            catRepository.deleteById(id);

            return true;
        }

        return false;
    }

    @Override
    public boolean deleteByEntity(Cat cat) {
        return deleteById(cat.getId());
    }

    @Override
    public void deleteAll() {
        catRepository.deleteAll();
    }

    @Override
    public boolean update(Cat cat) {
        if (catRepository.existsById(cat.getId())) {
            catRepository.deleteById(cat.getId());
            catRepository.save(cat);

            return true;
        }

        return false;
    }

    @Override
    public Cat getById(Long id) {
        return catRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cat> getAll() {
        return catRepository.findAll();
    }

    @Override
    public List<Cat> getAllByOwnerId(Long id) {
        return catRepository.findAllByOwner(id);
    }

    @Override
    public List<Cat> getAllByName(String name) {
        return catRepository.findAllByName(name);
    }
}