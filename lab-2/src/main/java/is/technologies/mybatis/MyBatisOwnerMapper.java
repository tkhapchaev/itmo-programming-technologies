package is.technologies.mybatis;

import is.technologies.entities.Owner;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MyBatisOwnerMapper {
    @Insert("INSERT INTO lab.cats.owner (id, name, birthdate) VALUES (#{id}, #{name}, #{birthdate})")
    void save(Owner owner);

    @Delete("DELETE FROM lab.cats.owner WHERE id = #{id}")
    void deleteById(long id);

    @Delete("DELETE FROM lab.cats.owner WHERE id = #{id}")
    void deleteByEntity(Owner owner);

    @Delete("DELETE FROM lab.cats.owner")
    void deleteAll();

    @Update("UPDATE lab.cats.owner SET name = #{name}, birthdate = #{birthdate} WHERE id = #{id}")
    void update(Owner owner);

    @Select("SELECT * FROM lab.cats.owner WHERE id = #{id}")
    Owner getById(long id);

    @Select("SELECT * FROM lab.cats.owner")
    List<Owner> getAll();
}