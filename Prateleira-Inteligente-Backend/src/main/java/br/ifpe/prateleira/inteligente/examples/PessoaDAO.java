package br.ifpe.prateleira.inteligente.examples;
	
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class PessoaDAO {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-basic");
    EntityManager em = emf.createEntityManager();
    
    public void salvar(Pessoa pessoa) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(pessoa);
        em.getTransaction().commit();
        em.close();
    }

    public Pessoa buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Pessoa pessoa = em.find(Pessoa.class, id);
        em.close();
        return pessoa;
    }

    public List<Pessoa> listar() {
        EntityManager em = emf.createEntityManager();
        List<Pessoa> pessoas = em.createQuery("FROM Pessoa", Pessoa.class).getResultList();
        em.close();
        return pessoas;
    }

    public void atualizar(Pessoa pessoa) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(pessoa);
        em.getTransaction().commit();
        em.close();
    }

    public void remover(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Pessoa pessoa = em.find(Pessoa.class, id);
        if (pessoa != null) {
            em.remove(pessoa);
        }
        em.getTransaction().commit();
        em.close();
    }
}

