package is.technologies.hibernate;

import is.technologies.entities.Owner;
import is.technologies.repository.OwnerRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateOwnerRepository implements OwnerRepository {
    @Override
    public Owner save(Owner owner) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(owner);
        transaction.commit();
        session.close();

        return owner;
    }

    @Override
    public void deleteById(long id) {
        Owner owner = getById(id);
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteByEntity(Owner owner) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaDelete<Owner> criteria = builder.createCriteriaDelete(Owner.class);
        criteria.from(Owner.class);
        session.close();
    }

    @Override
    public Owner update(Owner owner) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(owner);
        transaction.commit();
        session.close();

        return owner;
    }

    @Override
    public Owner getById(long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Owner.class, id);
    }

    @Override
    public List<Owner> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Owner> criteria = builder.createQuery(Owner.class);
        criteria.from(Owner.class);
        List<Owner> owners = session.createQuery(criteria).getResultList();
        session.close();

        return owners;
    }
}