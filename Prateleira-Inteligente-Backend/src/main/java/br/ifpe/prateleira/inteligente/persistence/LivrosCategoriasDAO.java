package br.ifpe.prateleira.inteligente.persistence;

import br.ifpe.prateleira.inteligente.entities.LivrosCategorias;
import javax.persistence.*;

public class LivrosCategoriasDAO {
    // verificar erros depois
    // private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("prateleiraPU");

    public void adicionar(LivrosCategorias livrosCategorias) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(livrosCategorias);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao adicionar: " + e.getMessage());
        } finally {
            em.close();
        }

    }



}
