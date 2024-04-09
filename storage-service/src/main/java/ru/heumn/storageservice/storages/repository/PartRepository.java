package ru.heumn.storageservice.storages.repository;

import org.hibernate.SessionFactory;
import ru.heumn.storageservice.storages.entity.PartEntity;

public class PartRepository extends BaseRepository<Long, PartEntity>{

    public PartRepository(SessionFactory sessionFactory) {

        super(sessionFactory, PartEntity.class);
    }
}
