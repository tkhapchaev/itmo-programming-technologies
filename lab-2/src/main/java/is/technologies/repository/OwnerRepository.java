package is.technologies.repository;

import is.technologies.entities.Owner;

import java.sql.SQLException;
import java.util.List;

public interface OwnerRepository {
    Owner save(Owner owner) throws SQLException;

    void deleteById(long id) throws SQLException;

    void deleteByEntity(Owner owner) throws SQLException;

    void deleteAll() throws SQLException;

    Owner update(Owner owner) throws SQLException;

    Owner getById(long id) throws SQLException;

    List<Owner> getAll() throws SQLException;
}