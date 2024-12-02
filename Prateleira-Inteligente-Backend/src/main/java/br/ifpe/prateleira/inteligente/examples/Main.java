package br.ifpe.prateleira.inteligente.examples;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-basic");
        EntityManager em = emf.createEntityManager();

        // Criar Pessoa
        em.getTransaction().begin();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João Silva");
        em.persist(pessoa);
        em.getTransaction().commit();

        // Criar Endereço
        em.getTransaction().begin();
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua A");
        endereco.setCidade("Recife");
        endereco.setPessoa(pessoa);
        em.persist(endereco);
        em.getTransaction().commit();

        // Buscar Pessoa e Endereço
        Pessoa pessoaBuscada = em.find(Pessoa.class, pessoa.getId());
        System.out.println("Pessoa: " + pessoaBuscada.getNome());

        Endereco enderecoBuscado = em.find(Endereco.class, endereco.getId());
        System.out.println("Endereço: " + enderecoBuscado.getLogradouro() + ", " + enderecoBuscado.getCidade());

        em.close();
        emf.close();
    }
}

