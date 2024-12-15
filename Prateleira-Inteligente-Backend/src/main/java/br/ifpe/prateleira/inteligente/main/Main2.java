package br.ifpe.prateleira.inteligente.main;

import br.ifpe.prateleira.inteligente.entities.*;
import br.ifpe.prateleira.inteligente.persistence.*;

import java.sql.Date;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        // Criando DAOs
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        LivroDAO livroDAO = new LivroDAO();
        EstanteDAO estanteDAO = new EstanteDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ComentarioDAO comentarioDAO = new ComentarioDAO();
        LivrosCategoriasDAO livrosCategoriasDAO = new LivrosCategoriasDAO();
        EstanteLivroDAO estanteLivroDAO = new EstanteLivroDAO();


            // Criando Categorias
            Categoria categoria1 = new Categoria("Ficção Científica");
            Categoria categoria2 = new Categoria("Romance");
            categoriaDAO.adicionar(categoria1);
            categoriaDAO.adicionar(categoria2);

            // Criando Livros
            Livro livro1 = new Livro("Duna", "Frank Herbert", Date.valueOf("1965-08-01"), "Clássico da ficção científica", "Editora Aleph");
            Livro livro2 = new Livro("Orgulho e Preconceito", "Jane Austen", Date.valueOf("1813-01-28"), "Um romance clássico", "Editora Penguin");
            livroDAO.adicionar(livro1);
            livroDAO.adicionar(livro2);

            // Associando Livros e Categorias
            LivrosCategorias livrosCategorias1 = new LivrosCategorias(livro1, categoria1);
            LivrosCategorias livrosCategorias2 = new LivrosCategorias(livro2, categoria2);
            livrosCategoriasDAO.adicionar(livrosCategorias1);
            livrosCategoriasDAO.adicionar(livrosCategorias2);

            // Criando Estante
            Estante estante1 = new Estante("Estante de Ficção");
            estanteDAO.adicionar(estante1);

            // Associando Estante e Livros
            EstanteLivro estanteLivro1 = new EstanteLivro(estante1, livro1);
            EstanteLivro estanteLivro2 = new EstanteLivro(estante1, livro2);
            estanteLivroDAO.adicionar(estanteLivro1);
            estanteLivroDAO.adicionar(estanteLivro2);

            // Criando Usuario
            Usuario usuario1 = new Usuario(estante1, "João da Silva");
            usuarioDAO.adicionar(usuario1);

            // Criando Comentários
            Comentario comentario1 = new Comentario(new java.util.Date(), livro1, "Um clássico incrível!", usuario1);
            Comentario comentario2 = new Comentario(new java.util.Date(), livro2, "Leitura obrigatória para todos!", usuario1);
            comentarioDAO.adicionar(comentario1);
            comentarioDAO.adicionar(comentario2);

            // Listando categorias usando CategoriaDAO
            System.out.println("Categorias disponíveis:");
            List<Categoria> categorias = categoriaDAO.listarTodas();
            for (Categoria categoria : categorias) {
                System.out.println("- " + categoria.getNome());
            }

            // Listando livros
            System.out.println("\nLivros disponíveis:");
            List<Livro> livros = livroDAO.listarTodos();
            for (Livro livro : livros) {
                System.out.println("- " + livro.getTitulo() + " por " + livro.getAutor());
            }

            // Listando estantes
            System.out.println("\nEstantes disponíveis:");
            List<Estante> estantes = estanteDAO.listarTodos();
            for (Estante estante : estantes) {
                System.out.println("- " + estante.getDescricao());
            }

            // Listando usuários
            System.out.println("\nUsuários disponíveis:");
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            for (Usuario usuario : usuarios) {
                System.out.println("- " + usuario.getNome());
            }

            // Listando comentários
            System.out.println("\nComentários disponíveis:");
            List<Comentario> comentarios = comentarioDAO.listarTodos();
            for (Comentario comentario : comentarios) {
                System.out.println("- [" + comentario.getData() + "] " + comentario.getTexto());
            }
    }
}
