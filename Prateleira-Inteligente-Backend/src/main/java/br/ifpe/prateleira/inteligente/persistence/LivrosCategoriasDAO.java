package br.ifpe.prateleira.inteligente.persistence;

import br.ifpe.prateleira.inteligente.entities.LivrosCategorias;
import javax.persistence.*;
import java.util.List;

public class LivrosCategoriasDAO {
    // verificar erros depois
     private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("prateleiraPU");

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

    public List<LivrosCategorias> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM LivrosCategorias", LivrosCategorias.class).getResultList();
        } finally {
            em.close();
        }
    }

    public LivrosCategorias buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(LivrosCategorias.class, id);
        } finally {
            em.close();
        }
    }

    public void remover(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            LivrosCategorias lc = em.find(LivrosCategorias.class, id);
            if (lc != null) {
                em.remove(lc);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao remover LivrosCategorias: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
