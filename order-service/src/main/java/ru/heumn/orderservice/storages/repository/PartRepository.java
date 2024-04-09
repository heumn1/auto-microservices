package ru.heumn.orderservice.storages.repository;

import org.hibernate.SessionFactory;
import ru.heumn.orderservice.storages.entity.PartEntity;

public class PartRepository extends BaseRepository<Long, PartEntity>{
    public PartRepository(SessionFactory sessionFactory) {

        super(sessionFactory, PartEntity.class);
    }
}
