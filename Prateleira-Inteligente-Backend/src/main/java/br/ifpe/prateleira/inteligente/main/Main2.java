package br.ifpe.prateleira.inteligente.main;

import br.ifpe.prateleira.inteligente.entities.*;
import br.ifpe.prateleira.inteligente.persistence.CategoriaDAO;
import br.ifpe.prateleira.inteligente.ultil.JPAUtil;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        // Configurando EntityManager
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // Criando Categoria
            Categoria categoria1 = new Categoria(null, "Ficção Científica");
            Categoria categoria2 = new Categoria(null, "Romance");

            em.persist(categoria1);
            em.persist(categoria2);

            // Criando Livro
            Livro livro1 = new Livro(null, "Duna", "Frank Herbert", Date.valueOf("1965-08-01"), "Clássico da ficção científica", "Editora Aleph");
            Livro livro2 = new Livro(null, "Orgulho e Preconceito", "Jane Austen", Date.valueOf("1813-01-28"), "Um romance clássico", "Editora Penguin");

            em.persist(livro1);
            em.persist(livro2);

            // Associando Livro e Categoria
            LivrosCategorias livrosCategorias1 = new LivrosCategorias(null, livro1, categoria1);
            LivrosCategorias livrosCategorias2 = new LivrosCategorias(null, livro2, categoria2);

            em.persist(livrosCategorias1);
            em.persist(livrosCategorias2);

            // Criando Estante
            Estante estante1 = new Estante("Estante de Ficção");
            em.persist(estante1);

            // Associando Estante e Livro
            EstanteLivro estanteLivro1 = new EstanteLivro(estante1, livro1);
            EstanteLivro estanteLivro2 = new EstanteLivro(estante1, livro2);

            em.persist(estanteLivro1);
            em.persist(estanteLivro2);

            // Criando Usuario
            Usuario usuario1 = new Usuario(estante1, "João da Silva");
            em.persist(usuario1);

            // Atualizando relação bidirecional
            estante1.setUsuario(usuario1);
            em.merge(estante1);

            // Criando Comentários
            Comentario comentario1 = new Comentario(new java.util.Date(), livro1, "Um clássico incrível!", usuario1);
            Comentario comentario2 = new Comentario(new java.util.Date(), livro2, "Leitura obrigatória para todos!", usuario1);

            em.persist(comentario1);
            em.persist(comentario2);

            em.getTransaction().commit();

            // Listando categorias usando CategoriaDAO
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            List<Categoria> categorias = categoriaDAO.listarTodas();

            System.out.println("Categorias disponíveis:");
            for (Categoria categoria : categorias) {
                System.out.println("- " + categoria.getNome());
            }

        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
