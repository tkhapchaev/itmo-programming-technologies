package ru.tkhapchaev.services.owner;

import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.Owner;
import ru.tkhapchaev.repositories.OwnerRepository;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner save(Owner owner) {
        ownerRepository.save(owner);

        return owner;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ownerRepository.existsById(id)) {
            ownerRepository.deleteById(id);

            return true;
        }

        return false;
    }

    @Override
    public boolean deleteByEntity(Owner owner) {
        return deleteById(owner.getId());
    }

    @Override
    public void deleteAll() {
        ownerRepository.deleteAll();
    }

    @Override
    public boolean update(Owner owner) {
        if (ownerRepository.existsById(owner.getId())) {
            ownerRepository.deleteById(owner.getId());
            ownerRepository.save(owner);

            return true;
        }

        return false;
    }

    @Override
    public Owner getById(Long id) {
        return ownerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Owner> getAll() {
        return ownerRepository.findAll();
    }

    @Override
    public List<Owner> getAllByName(String name) {
        return ownerRepository.findAllByName(name);
    }
}