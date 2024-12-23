package br.ifpe.prateleira.inteligente.main;

import br.ifpe.prateleira.inteligente.entities.*;
import br.ifpe.prateleira.inteligente.persistence.*;


import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            LivroDAO livroDAO = new LivroDAO();
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            LivrosCategoriasDAO livrosCategoriasDAO = new LivrosCategoriasDAO();

            Categoria romance = new Categoria(null, "Romance");
            Categoria ficcao = new Categoria(null, "Ficção Científica");
            Categoria fantasia = new Categoria(null, "Fantasia");
            categoriaDAO.adicionar(romance);
            categoriaDAO.adicionar(ficcao);
            categoriaDAO.adicionar(fantasia);

            Livro livro1 = new Livro("Orgulho e Preconceito", "Jane Austen",
                    Date.valueOf(LocalDate.of(1813, 1, 28)), "Um clássico da literatura", "Penguin", null, null, new ArrayList<>());
            Livro livro2 = new Livro("Duna", "Frank Herbert",
                    Date.valueOf(LocalDate.of(1965, 8, 1)), "O início da saga", "Aleph", null, null, new ArrayList<>());
            Livro livro3 = new Livro("O Hobbit", "J.R.R. Tolkien",
                    Date.valueOf(LocalDate.of(1937, 9, 21)), "A aventura de Bilbo", "HarperCollins", null, null, new ArrayList<>());
            livroDAO.adicionar(livro1);
            livroDAO.adicionar(livro2);
            livroDAO.adicionar(livro3);

            LivrosCategorias lc1 = new LivrosCategorias(livro1, romance);
            LivrosCategorias lc2 = new LivrosCategorias(livro2, ficcao);
            LivrosCategorias lc3 = new LivrosCategorias(livro3, fantasia);
            LivrosCategorias lc4 = new LivrosCategorias(livro2, fantasia);
            livrosCategoriasDAO.adicionar(lc1);
            livrosCategoriasDAO.adicionar(lc2);
            livrosCategoriasDAO.adicionar(lc3);
            livrosCategoriasDAO.adicionar(lc4);

            Usuario usuario1 = new Usuario("João da Silva", new ArrayList<>(),new ArrayList<>());
            Usuario usuario2 = new Usuario("Maria Oliveira", new ArrayList<>(), new ArrayList<>());
            usuarioDAO.adicionar(usuario1);
            usuarioDAO.adicionar(usuario2);


            UsuarioLivro usuarioLivro1 = new UsuarioLivro(usuario1, livro1);
            UsuarioLivro usuarioLivro2 = new UsuarioLivro(usuario2,livro2);
            UsuarioLivro usuarioLivro3 = new UsuarioLivro(usuario2,livro3);
            usuario1.getUsuarioLivros().add(usuarioLivro1);
            usuario2.getUsuarioLivros().add(usuarioLivro2);
            usuario2.getUsuarioLivros().add(usuarioLivro3);

            livroDAO.atualizar(livro1);
            livroDAO.atualizar(livro2);
            livroDAO.atualizar(livro3);


            exibirLivrosPorCategoria(livroDAO, livrosCategoriasDAO);
            exibirUsuariosEEstantes(usuarioDAO);

        } catch (Exception e) {
            System.err.println("Erro na execução do programa: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void exibirLivrosPorCategoria(LivroDAO livroDAO, LivrosCategoriasDAO livrosCategoriasDAO) {
        System.out.println("Livros e Categorias:");
        List<Livro> livros = livroDAO.listarTodos();
        if (livros != null && !livros.isEmpty()) {
            for (Livro livro : livros) {
                System.out.println("Livro: " + livro.getTitulo() + " | Autor: " + livro.getAutor());
                List<LivrosCategorias> categorias = livrosCategoriasDAO.buscarPorLivro(livro);
                if (categorias != null && !categorias.isEmpty()) {
                    for (LivrosCategorias lc : categorias) {
                        System.out.println("  - Categoria: " + lc.getCategoria().getNome());
                    }
                } else {
                    System.out.println("  - Sem categorias associadas.");
                }
            }
        } else {
            System.out.println("Nenhum livro encontrado.");
        }
    }


    private static void exibirUsuariosEEstantes(UsuarioDAO usuarioDAO) {
        System.out.println("\nUsuários e suas Estantes:");
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        if (usuarios != null) {
            for (Usuario usuario : usuarios) {
                System.out.println("Usuário: " + usuario.getNome());
            }
        } else {
            System.out.println("Nenhum usuário encontrado.");
        }
    }
}