package ru.heumn.storageservice.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {

    private static final SessionFactory sessionFactory = start();

    private HibernateUtil(){}

    private static SessionFactory start(){
        Configuration configuration = new Configuration().configure();
        configuration.configure();
        return configuration.buildSessionFactory();
    }


    public static SessionFactory buildSessionFactory() {
        return sessionFactory;
    }
}
