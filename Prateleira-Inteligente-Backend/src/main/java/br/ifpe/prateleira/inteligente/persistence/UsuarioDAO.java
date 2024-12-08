package br.ifpe.prateleira.inteligente.persistence;

import br.ifpe.prateleira.inteligente.entities.Usuario;

import javax.persistence.*;
import java.util.List;

public class UsuarioDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("prateleiraPU");

    // Adicionar um usuário
    public void adicionar(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao adicionar usuário: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Buscar usuário por ID
    public Usuario buscarPorId(Long idUsuario) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Usuario.class, idUsuario);
        } finally {
            em.close();
        }
    }

    // Listar todos os usuários
    public List<Usuario> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Usuario", Usuario.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Atualizar um usuário
    public void atualizar(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Deletar um usuário
    public void deletar(Long idUsuario) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, idUsuario);
            if (usuario != null) {
                em.remove(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Fechar o EntityManagerFactory
    public static void fechar() {
        emf.close();
    }
}
