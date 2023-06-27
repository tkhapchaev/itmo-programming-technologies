package ru.tkhapchaev.services.owner;

import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.Cat;
import ru.tkhapchaev.entities.Owner;
import ru.tkhapchaev.repositories.OwnerRepository;
import ru.tkhapchaev.services.cat.CatService;
import ru.tkhapchaev.services.user.UserService;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    private final CatService catService;

    private final UserService userService;

    public OwnerServiceImpl(OwnerRepository ownerRepository, CatService catService, UserService userService) {
        this.ownerRepository = ownerRepository;
        this.catService = catService;
        this.userService = userService;
    }

    @Override
    public Owner save(Owner owner) {
        ownerRepository.save(owner);

        return owner;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ownerRepository.existsById(id)) {
            List<Cat> cats = getById(id).getCats();

            for (Cat cat : cats) {
                catService.deleteById(cat.getId());
            }

            userService.deleteById(getById(id).getUser().getId());
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
        List<Owner> owners = getAll();

        for (Owner owner : owners) {
            deleteById(owner.getId());
        }
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