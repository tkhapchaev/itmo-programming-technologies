package is.technologies.jdbc;

import is.technologies.entities.Owner;
import is.technologies.repository.OwnerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class JdbcOwnerRepository implements OwnerRepository {
    private Connection connection;

    @Override
    public Owner save(Owner owner) throws SQLException {
        String query = "INSERT INTO lab.cats.owner (id, name, birthdate) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, owner.getId());
        statement.setString(2, owner.getName());
        statement.setDate(3, Date.valueOf(owner.getBirthdate()));

        statement.executeUpdate();

        return owner;
    }

    @Override
    public void deleteById(long id) throws SQLException {
        String query = "DELETE FROM lab.cats.owner WHERE id = " + id;
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }

    @Override
    public void deleteByEntity(Owner owner) throws SQLException {
        String query = "DELETE FROM lab.cats.owner WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, owner.getId());

        statement.executeUpdate();
    }

    @Override
    public void deleteAll() throws SQLException {
        String query = "DELETE FROM lab.cats.owner";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }

    @Override
    public Owner update(Owner owner) throws SQLException {
        String query = "UPDATE lab.cats.owner SET name = ?, birthdate = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(3, owner.getId());
        statement.setString(1, owner.getName());
        statement.setDate(2, Date.valueOf(owner.getBirthdate()));

        statement.executeUpdate();

        return owner;
    }

    @Override
    public Owner getById(long id) throws SQLException {
        String query = "SELECT * FROM lab.cats.owner WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();
        Long ownerId = resultSet.getLong(1);
        String name = resultSet.getString(2);
        LocalDate birthdate = resultSet.getDate(3).toLocalDate();

        return new Owner(ownerId, name, birthdate);
    }

    @Override
    public List<Owner> getAll() throws SQLException {
        ArrayList<Owner> result = new ArrayList<>();

        String query = "SELECT * FROM lab.cats.owner";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            LocalDate birthdate = resultSet.getDate(3).toLocalDate();

            result.add(new Owner(id, name, birthdate));
        }

        return result;
    }
}