package br.ifpe.prateleira.inteligente.entities;

import javax.persistence.*;
import java.util.List;


public class LivroDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-prateleira");
    EntityManager em = emf.createEntityManager();

    public void adicionar(Livro livro) {
        try {
            em.getTransaction().begin();
            em.persist(livro);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

        }

    }

    public Livro buscarId(Long id) {
        try {
            return em.find(Livro.class, id);
        } catch (Exception e) {
            return null;
        }
    }
    public List<Livro> listarTodos() {
        try {
            return em.createQuery("FROM Livro", Livro.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public void atualizar(Livro livro) {
        try {
            em.getTransaction().begin();
            em.merge(livro);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

        }
    }

    public void deletar(Long id) {
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

