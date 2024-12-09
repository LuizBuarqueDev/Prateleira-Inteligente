package br.ifpe.prateleira.inteligente.persistence;

import br.ifpe.prateleira.inteligente.entities.Estante;

import javax.persistence.*;
import java.util.List;

public class EstanteDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("prateleiraPU");

    // Adicionar uma estante
    public void adicionar(Estante estante) {
        EntityManager em = emf.createEntityManager();
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

    // Buscar estante por ID
    public Estante buscarPorId(Long idEstante) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Estante.class, idEstante);
        } finally {
            em.close();
        }
    }

    // Listar todas as estantes
    public List<Estante> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Estante", Estante.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Atualizar uma estante
    public void atualizar(Estante estante) {
        EntityManager em = emf.createEntityManager();
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

    // Deletar uma estante
    public void deletar(Long idEstante) {
        EntityManager em = emf.createEntityManager();
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

    // Fechar o EntityManagerFactory
    public static void fechar() {
        emf.close();
    }
}
