package br.ifpe.prateleira.inteligente.entities;

import javax.persistence.*;

import java.util.List;

public class CategoriaDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("prateleiraPU");

    public void adicionar(Categoria categoria) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao adicionar categoria: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Categoria buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public List<Categoria> listarTodas() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Categoria", Categoria.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void atualizar(Categoria categoria) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(categoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao atualizar categoria: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void deletar(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Categoria categoria = em.find(Categoria.class, id);
            if (categoria != null) {
                em.remove(categoria);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao deletar categoria: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void fechar() {
        emf.close();
    }




}
