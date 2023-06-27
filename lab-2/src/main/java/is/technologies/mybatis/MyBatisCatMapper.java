package is.technologies.mybatis;

import is.technologies.entities.Cat;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MyBatisCatMapper {
    @Insert("INSERT INTO lab.cats.cat (id, name, birthdate, breed, colour, owner) VALUES (#{id}, #{name}, #{birthdate}, #{breed}, #{colour}, #{owner})")
    void save(Cat cat);

    @Delete("DELETE FROM lab.cats.cat WHERE id = #{id}")
    void deleteById(long id);

    @Delete("DELETE FROM lab.cats.cat WHERE id = #{id}")
    void deleteByEntity(Cat cat);

    @Delete("DELETE FROM lab.cats.cat")
    void deleteAll();

    @Update("UPDATE lab.cats.cat SET name = #{name}, birthdate = #{birthdate}, breed = #{breed}, colour = #{colour}, owner = #{owner} WHERE id = #{id}")
    void update(Cat cat);

    @Select("SELECT * FROM lab.cats.cat WHERE id = #{id}")
    Cat getById(long id);

    @Select("SELECT * FROM lab.cats.cat")
    List<Cat> getAll();

    @Select("SELECT * FROM lab.cats.cat WHERE owner = #{id}")
    List<Cat> getAllByOwnerId(long id);
}