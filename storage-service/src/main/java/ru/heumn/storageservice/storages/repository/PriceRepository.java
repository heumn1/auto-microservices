package ru.heumn.storageservice.storages.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.heumn.storageservice.storages.entity.PartEntity;
import ru.heumn.storageservice.storages.entity.PriceEntity;
import ru.heumn.storageservice.storages.entity.SupplierEntity;

import java.util.List;

public class PriceRepository extends BaseRepository<Long, PriceEntity>{

    SessionFactory sessionFactory;
    public PriceRepository(SessionFactory sessionFactory) {
        super(sessionFactory, PriceEntity.class);
        this.sessionFactory = sessionFactory;
    }

    public List<PriceEntity> getSupplierByPart(PartEntity entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<PriceEntity> priceEntityList = session.createQuery("" +
                        "select s from PriceEntity s where s.part = :partS" +
                        "", PriceEntity.class).setParameter("partS", entity).list();
        session.getTransaction().commit();

        return priceEntityList;
    }

    public List<PriceEntity> getPartBySupplier(SupplierEntity supplier) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<PriceEntity> priceEntityList = session.createQuery("" +
                "select p from PriceEntity p where p.supplier = :supplierP" +
                "", PriceEntity.class).setParameter("supplierP", supplier).list();
        session.getTransaction().commit();

        return priceEntityList;
    }
}
