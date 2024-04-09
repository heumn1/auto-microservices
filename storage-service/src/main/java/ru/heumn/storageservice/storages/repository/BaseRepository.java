package ru.heumn.storageservice.storages.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BaseRepository<K extends Serializable, E> implements Repository<K, E> {

    private final SessionFactory sessionFactory;
    private final Class<E> clazz;

    @Override
    public void save(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
    }

    @Override
    public void delete(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.remove(entity);
        session.getTransaction().commit();
    }

    @Override
    public void update(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(entity);
        session.getTransaction().commit();
    }

    @Override
    public Optional<E> findById(K id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Optional<E> object = Optional.ofNullable(session.find(clazz, id));
        session.getTransaction().commit();
        return object;
    }

    @Override
    public List<E> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        var criteria = session.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);
        List<E> list = session.createQuery(criteria).getResultList();
        session.getTransaction().commit();
        return list;
    }
}
