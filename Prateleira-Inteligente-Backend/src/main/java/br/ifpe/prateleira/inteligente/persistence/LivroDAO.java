package br.ifpe.prateleira.inteligente.persistence;

import br.ifpe.prateleira.inteligente.entities.Livro;
import br.ifpe.prateleira.inteligente.ultil.JPAUtil;

import javax.persistence.*;
import java.util.List;


public class LivroDAO {
    public void adicionar(Livro livro) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(livro);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

        }

    }

    public Livro buscarId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Livro.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Livro> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("FROM Livro", Livro.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public void atualizar(Livro livro) {
        EntityManager em = JPAUtil.getEntityManager();


        try {
            em.getTransaction().begin();
            em.merge(livro);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

        }
    }

    public void deletar(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Livro livro = em.find(Livro.class, id);
            if (livro != null) {
                em.remove(livro);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

        }
    }
}

