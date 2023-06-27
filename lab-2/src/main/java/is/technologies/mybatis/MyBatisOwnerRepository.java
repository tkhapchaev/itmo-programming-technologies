package is.technologies.mybatis;

import is.technologies.entities.Owner;
import is.technologies.repository.OwnerRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class MyBatisOwnerRepository implements OwnerRepository {
    @Override
    public Owner save(Owner owner) {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisOwnerMapper entityMapper = session.getMapper(MyBatisOwnerMapper.class);
        entityMapper.save(owner);
        session.commit();
        session.close();

        return owner;
    }

    @Override
    public void deleteById(long id) {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisOwnerMapper entityMapper = session.getMapper(MyBatisOwnerMapper.class);
        entityMapper.deleteById(id);
        session.commit();
        session.close();
    }

    @Override
    public void deleteByEntity(Owner owner) {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisOwnerMapper entityMapper = session.getMapper(MyBatisOwnerMapper.class);
        entityMapper.deleteByEntity(owner);
        session.commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisOwnerMapper entityMapper = session.getMapper(MyBatisOwnerMapper.class);
        entityMapper.deleteAll();
        session.commit();
        session.close();
    }

    @Override
    public Owner update(Owner owner) {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisOwnerMapper entityMapper = session.getMapper(MyBatisOwnerMapper.class);
        entityMapper.update(owner);
        session.commit();
        session.close();

        return owner;
    }

    public Owner getById(long id) {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisOwnerMapper entityMapper = session.getMapper(MyBatisOwnerMapper.class);
        Owner owner = entityMapper.getById(id);
        session.close();

        return owner;
    }

    public List<Owner> getAll() {
        SqlSession session = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        MyBatisOwnerMapper entityMapper = session.getMapper(MyBatisOwnerMapper.class);
        List<Owner> owners = entityMapper.getAll();
        session.close();

        return owners;
    }
}