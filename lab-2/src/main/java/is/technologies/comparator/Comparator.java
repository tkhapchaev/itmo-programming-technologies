package is.technologies.comparator;

import is.technologies.entities.Cat;
import is.technologies.entities.Owner;
import is.technologies.hibernate.HibernateCatRepository;
import is.technologies.jdbc.JdbcCatRepository;
import is.technologies.enums.Colour;
import is.technologies.jdbc.JdbcOwnerRepository;
import is.technologies.mybatis.MyBatisCatRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Comparator {
    private final String url;

    private final String user;

    private final String password;

    private final List<Cat> cats;

    private final List<Owner> owners;

    public Comparator(String url, String user, String password) {
        cats = new ArrayList<>();
        owners = new ArrayList<>();
        List<String> colours = new ArrayList<>();

        this.url = url;
        this.user = user;
        this.password = password;

        colours.add(Colour.WHITE.toString());
        colours.add(Colour.RED.toString());
        colours.add(Colour.BROWN.toString());
        colours.add(Colour.GRAY.toString());
        colours.add(Colour.BLACK.toString());

        Random random = new Random();

        for (long i = 1; i <= 5; i++) {
            owners.add(new Owner(i, "Owner " + i, LocalDate.now()));
        }

        for (long j = 1; j <= 100; j++) {
            cats.add(new Cat(j, "Cat " + j, LocalDate.now(), "Breed " + j, colours.get(random.nextInt(5)), (long) random.nextInt(5) + 1));
        }
    }

    public List<String> Compare() throws SQLException {
        List<String> results = new ArrayList<>();
        Connection connection = DriverManager.getConnection(url, user, password);

        JdbcCatRepository jdbcCatRepository = new JdbcCatRepository(connection);
        jdbcCatRepository.deleteAll();

        JdbcOwnerRepository jdbcOwnerRepository = new JdbcOwnerRepository(connection);
        jdbcOwnerRepository.deleteAll();

        for (Owner owner : owners) {
            jdbcOwnerRepository.save(owner);
        }

        Instant start = Instant.now();

        for (Cat cat : cats) {
            jdbcCatRepository.save(cat);
        }

        Instant finish = Instant.now();
        long elapsed = Duration.between(start, finish).toMillis();
        results.add("Time spent saving 100 entities using JDBC: " + elapsed + " ms");

        start = Instant.now();
        List<Cat> cats = jdbcCatRepository.getAll();
        finish = Instant.now();
        elapsed = Duration.between(start, finish).toMillis();
        results.add("Time spent getting 100 entities using JDBC: " + elapsed + " ms");

        connection.close();

        MyBatisCatRepository myBatisCatRepository = new MyBatisCatRepository();
        myBatisCatRepository.deleteAll();

        start = Instant.now();

        for (Cat cat : cats) {
            myBatisCatRepository.save(cat);
        }

        finish = Instant.now();
        elapsed = Duration.between(start, finish).toMillis();
        results.add("Time spent saving 100 entities using MyBatis: " + elapsed + " ms");

        start = Instant.now();
        cats = myBatisCatRepository.getAll();
        finish = Instant.now();
        elapsed = Duration.between(start, finish).toMillis();
        results.add("Time spent getting 100 entities using MyBatis: " + elapsed + " ms");
        myBatisCatRepository.deleteAll();

        HibernateCatRepository hibernateCatRepository = new HibernateCatRepository();
        start = Instant.now();

        for (Cat cat : cats) {
            hibernateCatRepository.save(cat);
        }

        finish = Instant.now();
        elapsed = Duration.between(start, finish).toMillis();
        results.add("Time spent saving 100 entities using Hibernate: " + elapsed + " ms");

        start = Instant.now();
        cats = hibernateCatRepository.getAll();
        finish = Instant.now();
        elapsed = Duration.between(start, finish).toMillis();
        results.add("Time spent getting 100 entities using Hibernate: " + elapsed + " ms");

        return results;
    }
}