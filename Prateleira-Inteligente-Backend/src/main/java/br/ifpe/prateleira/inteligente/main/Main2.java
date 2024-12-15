package br.ifpe.prateleira.inteligente.main;

import br.ifpe.prateleira.inteligente.entities.*;
import br.ifpe.prateleira.inteligente.persistence.*;

import java.sql.Date;
import java.util.ArrayList;

public class Main2 {
    public static void main(String[] args) {
        // Criar instâncias dos DAOs
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        LivroDAO livroDAO = new LivroDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        EstanteDAO estanteDAO = new EstanteDAO();
        ComentarioDAO comentarioDAO = new ComentarioDAO();
        EstanteLivroDAO estanteLivroDAO = new EstanteLivroDAO();
        LivrosCategoriasDAO livrosCategoriasDAO = new LivrosCategoriasDAO();

        // Criar categorias
        Categoria categoria1 = new Categoria("Ficção Científica");
        Categoria categoria2 = new Categoria("Tecnologia");
        categoriaDAO.adicionar(categoria1);
        categoriaDAO.adicionar(categoria2);

        // Criar um usuário
        Usuario usuario1 = new Usuario("João Silva", null, new ArrayList<>());
        usuarioDAO.adicionar(usuario1);

        // Criar uma estante para o usuário
        Estante estante1 = new Estante("Estante de João");
        estante1.setUsuario(usuario1);
        estanteDAO.adicionar(estante1);
        usuario1.setEstante(estante1);
        usuarioDAO.atualizar(usuario1);

        // Criar um livro
        Livro livro1 = new Livro(
                "Duna",
                "Frank Herbert",
                Date.valueOf("1965-08-01"),
                "Um clássico da ficção científica",
                "Chilton Books",
                new ArrayList<>(), // estanteLivros
                new ArrayList<>(), // livrosCategorias
                new ArrayList<>()  // comentarios
        );

        Livro livro2 = new Livro(
                "Clean Code",
                "Robert C. Martin",
                Date.valueOf("2008-08-01"),
                "Guia para boas práticas de programação",
                "Pearson",
                new ArrayList<>(), // estanteLivros
                new ArrayList<>(), // livrosCategorias
                new ArrayList<>()  // comentarios
        );

        // Adicionar os livros ao banco
        livroDAO.adicionar(livro1);
        livroDAO.adicionar(livro2);

        // Relacionar Livro e Categoria (LivrosCategorias)
        LivrosCategorias rel1 = new LivrosCategorias(livro1, categoria1);
        LivrosCategorias rel2 = new LivrosCategorias(livro2, categoria2);
        livro1.getLivrosCategorias().add(rel1);
        livro2.getLivrosCategorias().add(rel2);
        livrosCategoriasDAO.adicionar(rel1);
        livrosCategoriasDAO.adicionar(rel2);

        // Relacionar Livro e Estante (EstanteLivro)
        EstanteLivro estanteLivro1 = new EstanteLivro(estante1, livro1);
        EstanteLivro estanteLivro2 = new EstanteLivro(estante1, livro2);
        livro1.getEstanteLivros().add(estanteLivro1);
        livro2.getEstanteLivros().add(estanteLivro2);
        estanteLivroDAO.adicionar(estanteLivro1);
        estanteLivroDAO.adicionar(estanteLivro2);

        // Criar comentários
        Comentario comentario1 = new Comentario(new java.util.Date(), livro1, "Livro incrível!", usuario1);
        Comentario comentario2 = new Comentario(new java.util.Date(), livro2, "Ótimo para melhorar a qualidade do código!", usuario1);
        livro1.getComentarios().add(comentario1);
        livro2.getComentarios().add(comentario2);
        comentarioDAO.adicionar(comentario1);
        comentarioDAO.adicionar(comentario2);

        // Exibir informações para validar os relacionamentos
        System.out.println("Livros na estante de " + usuario1.getNome() + ":");
        for (EstanteLivro el : estanteLivroDAO.listarTodos()) {
            System.out.println("- " + el.getLivro().getTitulo());
        }

        System.out.println("Categorias do livro '" + livro1.getTitulo() + "':");
        for (LivrosCategorias lc : livrosCategoriasDAO.listarTodos()) {
            if (lc.getLivro().getId().equals(livro1.getId())) {
                System.out.println("- " + lc.getCategoria().getNome());
            }
        }

        System.out.println("Comentários do livro '" + livro1.getTitulo() + "':");
        for (Comentario c : comentarioDAO.listarTodos()) {
            if (c.getLivro().getId().equals(livro1.getId())) {
                System.out.println(c.getUsuario().getNome() + ": " + c.getTexto());
            }
        }
    }
}