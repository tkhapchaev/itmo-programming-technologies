package ru.tkhapchaev.services.cat;

import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.Cat;
import ru.tkhapchaev.entities.Flea;
import ru.tkhapchaev.repositories.CatRepository;
import ru.tkhapchaev.services.flea.FleaService;

import java.util.List;

@Service
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;

    private final FleaService fleaService;

    public CatServiceImpl(CatRepository catRepository, FleaService fleaService) {
        this.catRepository = catRepository;
        this.fleaService = fleaService;
    }

    @Override
    public Cat save(Cat cat) {
        catRepository.save(cat);

        return cat;
    }

    @Override
    public boolean deleteById(Long id) {
        if (catRepository.existsById(id)) {
            List<Flea> fleas = getById(id).getFleas();

            for (Flea flea : fleas) {
                fleaService.deleteById(flea.getId());
            }

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
        List<Cat> cats = getAll();

        for (Cat cat : cats) {
            deleteById(cat.getId());
        }
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
        return catRepository.findAllByOwnerId(id);
    }

    @Override
    public List<Cat> getAllByName(String name) {
        return catRepository.findAllByName(name);
    }
}