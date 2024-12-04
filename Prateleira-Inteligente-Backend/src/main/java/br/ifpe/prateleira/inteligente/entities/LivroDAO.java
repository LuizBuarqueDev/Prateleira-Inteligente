package br.ifpe.prateleira.inteligente.entities;

import javax.persistence.*;


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

}
