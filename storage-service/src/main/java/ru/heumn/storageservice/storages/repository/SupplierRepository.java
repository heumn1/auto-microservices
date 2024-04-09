package ru.heumn.storageservice.storages.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.heumn.storageservice.storages.entity.SupplierEntity;

public class SupplierRepository extends BaseRepository<Long, SupplierEntity>{


    public SupplierRepository(SessionFactory sessionFactory) {
        super(sessionFactory, SupplierEntity.class);
    }
}
