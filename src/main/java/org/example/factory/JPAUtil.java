package org.example.factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("integrador2");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
