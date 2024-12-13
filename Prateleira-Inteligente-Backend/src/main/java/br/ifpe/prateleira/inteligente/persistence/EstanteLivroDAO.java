package br.ifpe.prateleira.inteligente.persistence;

import br.ifpe.prateleira.inteligente.entities.EstanteLivro;
import br.ifpe.prateleira.inteligente.ultil.JPAUtil;

import javax.persistence.*;
import java.util.List;

public class EstanteLivroDAO {

    // Adicionar um EstanteLivro
    public void adicionar(EstanteLivro estanteLivro) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(estanteLivro);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao adicionar EstanteLivro: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Listar todos os EstanteLivros
    public List<EstanteLivro> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("FROM EstanteLivro", EstanteLivro.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Buscar EstanteLivro por ID
    public EstanteLivro buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(EstanteLivro.class, id);
        } finally {
            em.close();
        }
    }

    // Remover EstanteLivro por ID
    public void remover(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            EstanteLivro estanteLivro = em.find(EstanteLivro.class, id);
            if (estanteLivro != null) {
                em.remove(estanteLivro);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao remover EstanteLivro: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}