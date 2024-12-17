package br.ifpe.prateleira.inteligente.persistence;

import br.ifpe.prateleira.inteligente.entities.UsuarioLivro;
import br.ifpe.prateleira.inteligente.ultil.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class UsuarioLivroDAO {

    public void adicionar(UsuarioLivro usuarioLivro) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuarioLivro);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public UsuarioLivro buscarId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(UsuarioLivro.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<UsuarioLivro> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM UsuarioLivro", UsuarioLivro.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public void atualizar(UsuarioLivro usuarioLivro) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuarioLivro);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public void deletar(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            UsuarioLivro usuarioLivro = em.find(UsuarioLivro.class, id);
            if (usuarioLivro != null) {
                em.remove(usuarioLivro);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
}