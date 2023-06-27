package is.technologies.repository;

import is.technologies.entities.Cat;

import java.sql.SQLException;
import java.util.List;

public interface CatRepository {
    Cat save(Cat cat) throws SQLException;

    void deleteById(long id) throws SQLException;

    void deleteByEntity(Cat cat) throws SQLException;

    void deleteAll() throws SQLException;

    Cat update(Cat cat) throws SQLException;

    Cat getById(long id) throws SQLException;

    List<Cat> getAll() throws SQLException;

    List<Cat> getAllByOwnerId(long id) throws SQLException;
}