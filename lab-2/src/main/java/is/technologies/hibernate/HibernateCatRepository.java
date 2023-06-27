package is.technologies.hibernate;

import is.technologies.entities.Cat;
import is.technologies.repository.CatRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateCatRepository implements CatRepository {
    @Override
    public Cat save(Cat cat) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(cat);
        transaction.commit();
        session.close();

        return cat;
    }

    @Override
    public void deleteById(long id) {
        Cat cat = getById(id);
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(cat);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteByEntity(Cat cat) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(cat);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaDelete<Cat> criteria = builder.createCriteriaDelete(Cat.class);
        criteria.from(Cat.class);
        session.close();
    }

    @Override
    public Cat update(Cat cat) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(cat);
        transaction.commit();
        session.close();

        return cat;
    }

    @Override
    public Cat getById(long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Cat.class, id);
    }

    @Override
    public List<Cat> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Cat> criteria = builder.createQuery(Cat.class);
        criteria.from(Cat.class);
        List<Cat> cats = session.createQuery(criteria).getResultList();
        session.close();

        return cats;
    }

    @Override
    public List<Cat> getAllByOwnerId(long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Cat> criteria = builder.createQuery(Cat.class);
        Root<Cat> root = criteria.from(Cat.class);
        criteria.where(builder.equal(root.get("owner"), id));
        List<Cat> cats = session.createQuery(criteria).getResultList();
        session.close();

        return cats;
    }
}