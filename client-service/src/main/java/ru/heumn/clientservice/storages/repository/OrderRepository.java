package ru.heumn.clientservice.storages.repository;

import org.hibernate.SessionFactory;
import ru.heumn.clientservice.storages.entity.OrderEntity;

public class OrderRepository extends BaseRepository<Long, OrderEntity>{
    public OrderRepository(SessionFactory sessionFactory) {
        super(sessionFactory, OrderEntity.class);
    }
}
