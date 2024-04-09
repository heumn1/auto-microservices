package ru.heumn.orderservice.storages.repository;

import org.hibernate.SessionFactory;
import ru.heumn.orderservice.storages.entity.OrderEntity;

public class OrderRepository extends BaseRepository<Long, OrderEntity>{
    public OrderRepository(SessionFactory sessionFactory) {
        super(sessionFactory, OrderEntity.class);
    }
}
