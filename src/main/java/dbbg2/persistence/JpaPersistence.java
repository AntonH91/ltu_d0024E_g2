package dbbg2.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaPersistence {
    public static final String PERSISTENCE_UNIT_NAME = "lddb_jpa";

    private static EntityManagerFactory factory;

    public static void startUp() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
    }

    public static EntityManager getEntityManager() {
        startUp();
        return factory.createEntityManager();
    }



}
