package is.technologies.jdbc;

import is.technologies.entities.Cat;
import is.technologies.repository.CatRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class JdbcCatRepository implements CatRepository {
    private Connection connection;

    @Override
    public Cat save(Cat cat) throws SQLException {
        String query = "INSERT INTO lab.cats.cat (id, name, birthdate, breed, colour, owner) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, cat.getId());
        statement.setString(2, cat.getName());
        statement.setDate(3, Date.valueOf(cat.getBirthdate()));
        statement.setString(4, cat.getBreed());
        statement.setString(5, cat.getColour());
        statement.setLong(6, cat.getOwner());

        statement.executeUpdate();

        return cat;
    }

    @Override
    public void deleteById(long id) throws SQLException {
        String query = "DELETE FROM lab.cats.cat WHERE id = " + id;
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }

    @Override
    public void deleteByEntity(Cat cat) throws SQLException {
        String query = "DELETE FROM lab.cats.cat WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, cat.getId());

        statement.executeUpdate();
    }

    @Override
    public void deleteAll() throws SQLException {
        String query = "DELETE FROM lab.cats.cat";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }

    @Override
    public Cat update(Cat cat) throws SQLException {
        String query = "UPDATE lab.cats.cat SET name = ?, birthdate = ?, breed = ?, colour = ?, owner = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(6, cat.getId());
        statement.setString(1, cat.getName());
        statement.setDate(2, Date.valueOf(cat.getBirthdate()));
        statement.setString(3, cat.getBreed());
        statement.setString(4, cat.getColour());
        statement.setLong(5, cat.getOwner());

        statement.executeUpdate();

        return cat;
    }

    @Override
    public Cat getById(long id) throws SQLException {
        String query = "SELECT * FROM lab.cats.cat WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();
        Long catId = resultSet.getLong(1);
        String name = resultSet.getString(2);
        LocalDate birthdate = resultSet.getDate(3).toLocalDate();
        String breed = resultSet.getString(4);
        String colour = resultSet.getString(5);
        Long owner = resultSet.getLong(6);

        return new Cat(catId, name, birthdate, breed, colour, owner);
    }

    @Override
    public List<Cat> getAll() throws SQLException {
        ArrayList<Cat> result = new ArrayList<>();

        String query = "SELECT * FROM lab.cats.cat";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            LocalDate birthdate = resultSet.getDate(3).toLocalDate();
            String breed = resultSet.getString(4);
            String colour = resultSet.getString(5);
            Long owner = resultSet.getLong(6);

            result.add(new Cat(id, name, birthdate, breed, colour, owner));
        }

        return result;
    }

    @Override
    public List<Cat> getAllByOwnerId(long id) throws SQLException {
        ArrayList<Cat> result = new ArrayList<>();

        String query = "SELECT * FROM lab.cats.cat WHERE owner = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Long catId = resultSet.getLong(1);
            String name = resultSet.getString(2);
            LocalDate birthdate = resultSet.getDate(3).toLocalDate();
            String breed = resultSet.getString(4);
            String colour = resultSet.getString(5);
            Long owner = resultSet.getLong(6);

            result.add(new Cat(catId, name, birthdate, breed, colour, owner));
        }

        return result;
    }
}