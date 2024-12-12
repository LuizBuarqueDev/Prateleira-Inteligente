package br.ifpe.prateleira.inteligente.persistence;

import br.ifpe.prateleira.inteligente.entities.Estante;
import br.ifpe.prateleira.inteligente.ultil.JPAUtil;

import javax.persistence.*;
import java.util.List;

public class EstanteDAO {

    public void adicionar(Estante estante) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(estante);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao adicionar estante: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Estante buscarPorId(Long idEstante) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Estante.class, idEstante);
        } finally {
            em.close();
        }
    }

    public List<Estante> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("FROM Estante", Estante.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void atualizar(Estante estante) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(estante);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao atualizar estante: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void deletar(Long idEstante) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Estante estante = em.find(Estante.class, idEstante);
            if (estante != null) {
                em.remove(estante);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao deletar estante: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
