package br.ifpe.prateleira.inteligente.main;

import br.ifpe.prateleira.inteligente.entities.*;
import br.ifpe.prateleira.inteligente.persistence.*;

import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        LivroDAO livroDAO = new LivroDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        UsuarioLivroDAO usuarioLivroDAO = new UsuarioLivroDAO();
        LivrosCategoriasDAO livrosCategoriasDAO = new LivrosCategoriasDAO();


        Usuario usuario1 = new Usuario("Carlla Patricia", new ArrayList<>(), null);
        Usuario usuario2 = new Usuario("Vitoria Carla", new ArrayList<>(), null);
        usuarioDAO.adicionar(usuario1);
        usuarioDAO.adicionar(usuario2);


        Livro livro1 = new Livro("Título 1", "Autor X", Date.valueOf(LocalDate.of(2022, 1, 15)),
                "Livro sobre Java", "Editora Y", null, null, new ArrayList<>());
        Livro livro2 = new Livro("Título 2", "Autor Y", Date.valueOf(LocalDate.of(2021, 8, 10)),
                "Livro de programação", "Editora Z", null, null, new ArrayList<>());
        livroDAO.adicionar(livro1);
        livroDAO.adicionar(livro2);

        Categoria romance = new Categoria(null, "Romance");
        Categoria ficcao = new Categoria(null, "Ficção Científica");
        Categoria fantasia = new Categoria(null, "Fantasia");
        categoriaDAO.adicionar(romance);
        categoriaDAO.adicionar(ficcao);
        categoriaDAO.adicionar(fantasia);


        UsuarioLivro usuarioLivro1 = new UsuarioLivro(usuario1, livro1);
        UsuarioLivro usuarioLivro2 = new UsuarioLivro(usuario2, livro1);
        UsuarioLivro usuarioLivro3 = new UsuarioLivro(usuario2, livro2);

        usuarioLivroDAO.adicionar(usuarioLivro1);
        usuarioLivroDAO.adicionar(usuarioLivro2);
        usuarioLivroDAO.adicionar(usuarioLivro3);

        LivrosCategorias livrosCategorias1 = new LivrosCategorias(livro1, romance);
        LivrosCategorias livrosCategorias2 = new LivrosCategorias(livro1, ficcao);
        LivrosCategorias livrosCategorias3 = new LivrosCategorias(livro2, fantasia);

        livrosCategoriasDAO.adicionar(livrosCategorias1);
        livrosCategoriasDAO.adicionar(livrosCategorias2);
        livrosCategoriasDAO.adicionar(livrosCategorias3);

        System.out.println("Usuários e seus livros:");
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        for (Usuario usuario : usuarios) {
            System.out.println("Usuário: " + usuario.getNome());
            for (UsuarioLivro ul : usuarioLivroDAO.listarPorUsuario(usuario)) {
                System.out.println("  - Livro: " + ul.getLivro().getTitulo());
            }
        }

        System.out.println("\nLivros e suas categorias:");
        List<Livro> livros = livroDAO.listarTodos();
        for (Livro livro : livros) {
            System.out.println("Livro: " + livro.getTitulo());
            for (LivrosCategorias lc : livrosCategoriasDAO.buscarPorLivro(livro)) {
                System.out.println("  - Categoria: " + lc.getCategoria().getNome());
            }
        }
    }
}
