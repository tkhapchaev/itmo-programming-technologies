package is.technologies.mybatis;

import is.technologies.entities.Cat;
import is.technologies.repository.CatRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class MyBatisCatRepository implements CatRepository {
    @Override
    public Cat save(Cat cat) {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisCatMapper entityMapper = session.getMapper(MyBatisCatMapper.class);
        entityMapper.save(cat);
        session.commit();
        session.close();

        return cat;
    }

    @Override
    public void deleteById(long id) {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisCatMapper entityMapper = session.getMapper(MyBatisCatMapper.class);
        entityMapper.deleteById(id);
        session.commit();
        session.close();
    }

    @Override
    public void deleteByEntity(Cat cat) {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisCatMapper entityMapper = session.getMapper(MyBatisCatMapper.class);
        entityMapper.deleteByEntity(cat);
        session.commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisCatMapper entityMapper = session.getMapper(MyBatisCatMapper.class);
        entityMapper.deleteAll();
        session.commit();
        session.close();
    }

    @Override
    public Cat update(Cat cat) {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisCatMapper entityMapper = session.getMapper(MyBatisCatMapper.class);
        entityMapper.update(cat);
        session.commit();
        session.close();

        return cat;
    }

    public Cat getById(long id) {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisCatMapper entityMapper = session.getMapper(MyBatisCatMapper.class);
        Cat cat = entityMapper.getById(id);
        session.close();

        return cat;
    }

    public List<Cat> getAll() {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisCatMapper entityMapper = session.getMapper(MyBatisCatMapper.class);
        List<Cat> cats = entityMapper.getAll();
        session.close();

        return cats;
    }

    @Override
    public List<Cat> getAllByOwnerId(long id) {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisCatMapper entityMapper = session.getMapper(MyBatisCatMapper.class);
        List<Cat> cats = entityMapper.getAll();
        session.close();

        return cats;
    }
}