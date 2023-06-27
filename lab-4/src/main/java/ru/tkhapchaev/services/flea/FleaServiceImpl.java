package ru.tkhapchaev.services.flea;

import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.Flea;
import ru.tkhapchaev.repositories.FleaRepository;

import java.util.List;

@Service
public class FleaServiceImpl implements FleaService {
    private final FleaRepository fleaRepository;

    public FleaServiceImpl(FleaRepository fleaRepository) {
        this.fleaRepository = fleaRepository;
    }

    @Override
    public Flea save(Flea flea) {
        fleaRepository.save(flea);

        return flea;
    }

    @Override
    public boolean deleteById(Long id) {
        if (fleaRepository.existsById(id)) {
            fleaRepository.deleteById(id);

            return true;
        }

        return false;
    }

    @Override
    public boolean deleteByEntity(Flea flea) {
        return deleteById(flea.getId());
    }

    @Override
    public void deleteAll() {
        List<Flea> fleas = getAll();

        for (Flea flea : fleas) {
            deleteById(flea.getId());
        }
    }

    @Override
    public boolean update(Flea flea) {
        if (fleaRepository.existsById(flea.getId())) {
            fleaRepository.deleteById(flea.getId());
            fleaRepository.save(flea);

            return true;
        }

        return false;
    }

    @Override
    public Flea getById(Long id) {
        return fleaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Flea> getAll() {
        return fleaRepository.findAll();
    }

    @Override
    public List<Flea> getAllByCatId(Long id) {
        return fleaRepository.findAllByCatId(id);
    }
}