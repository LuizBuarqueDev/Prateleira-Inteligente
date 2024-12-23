package br.ifpe.prateleira.inteligente.ultil;

import javax.persistence.EntityManagerFactory;
import javax.persistence.*;

public class JPAUtil {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("prateleiraPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void closeFactory() {
        emf.close();
    }
}