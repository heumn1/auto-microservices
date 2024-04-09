package ru.heumn.clientservice.storages.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.heumn.clientservice.storages.entity.ClientEntity;

import java.util.List;
import java.util.Optional;

public class ClientRepository extends BaseRepository<Long, ClientEntity>{
    SessionFactory sessionFactory;

    public ClientRepository(SessionFactory sessionFactory) {
        super(sessionFactory, ClientEntity.class);
        this.sessionFactory = sessionFactory;
    }


    public Optional<ClientEntity> getClientByNumber(String number) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Optional<ClientEntity> client = session.createQuery("" +
                "select cl from ClientEntity cl where cl.number = :numberCl" +
                "", ClientEntity.class).setParameter("numberCl", number).stream().findFirst();
        session.getTransaction().commit();

        return client;
    }
}
