package br.ifpe.prateleira.inteligente.persistence;

import br.ifpe.prateleira.inteligente.entities.Comentario;
import br.ifpe.prateleira.inteligente.ultil.JPAUtil;

import javax.persistence.*;
import java.util.List;

public class ComentarioDAO {
    public void adicionar(Comentario comentario) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(comentario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao adicionar comentário: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Comentario buscarPorId(Long idComentario) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Comentario.class, idComentario);
        } finally {
            em.close();
        }
    }

    public List<Comentario> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("FROM Comentario", Comentario.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void atualizar(Comentario comentario) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(comentario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao atualizar comentário: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void deletar(Long idComentario) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Comentario comentario = em.find(Comentario.class, idComentario);
            if (comentario != null) {
                em.remove(comentario);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao deletar comentário: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
