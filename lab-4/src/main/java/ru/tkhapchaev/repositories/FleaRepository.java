package ru.tkhapchaev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkhapchaev.entities.Flea;

import java.util.List;

@Repository
public interface FleaRepository extends JpaRepository<Flea, Long> {
    List<Flea> findAllByCatId(Long id);
}