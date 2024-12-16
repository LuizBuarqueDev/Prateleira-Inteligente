package br.ifpe.prateleira.inteligente.persistence;

import br.ifpe.prateleira.inteligente.entities.LivrosCategorias;
import br.ifpe.prateleira.inteligente.ultil.JPAUtil;
import br.ifpe.prateleira.inteligente.entities.*;
import javax.persistence.*;
import java.util.List;

public class LivrosCategoriasDAO {

    public void adicionar(LivrosCategorias livrosCategorias) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(livrosCategorias);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            System.err.println("Erro ao adicionar: " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<LivrosCategorias> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("FROM LivrosCategorias", LivrosCategorias.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<LivrosCategorias> buscarPorLivro(Livro livro) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("SELECT lc FROM LivrosCategorias lc WHERE lc.livro = :livro", LivrosCategorias.class)
                    .setParameter("livro", livro)
                    .getResultList();
        } finally {
            em.close();
        }
    }



    public void remover(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

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
