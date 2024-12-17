package br.ifpe.prateleira.inteligente.main;

import br.ifpe.prateleira.inteligente.entities.Comentario;
import br.ifpe.prateleira.inteligente.entities.Livro;
import br.ifpe.prateleira.inteligente.entities.Usuario;
import br.ifpe.prateleira.inteligente.persistence.ComentarioDAO;
import br.ifpe.prateleira.inteligente.persistence.LivroDAO;
import br.ifpe.prateleira.inteligente.persistence.UsuarioDAO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        // Instanciar os DAOs
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        LivroDAO livroDAO = new LivroDAO();
        ComentarioDAO comentarioDAO = new ComentarioDAO();

        // Criar e adicionar usuários
        Usuario usuario1 = new Usuario("João da Silva", new ArrayList<>(), null);
        Usuario usuario2 = new Usuario("Maria Oliveira", new ArrayList<>(), null);
        usuarioDAO.adicionar(usuario1);
        usuarioDAO.adicionar(usuario2);

        // Criar e adicionar livros
        Livro livro1 = new Livro("Java", "Autor X", Date.valueOf(LocalDate.of(2022, 1, 15)), "Livro sobre Java", "Editora x", null, null, new ArrayList<>());
        Livro livro2 = new Livro("Spring", "Autor Y", Date.valueOf(LocalDate.of(2021, 8, 10)), "Livro sobre Spring", "Editora y", null, null, new ArrayList<>());
        Livro livro3 = new Livro("Vue3", "Autor Z", Date.valueOf(LocalDate.of(2021, 8, 10)), "Livro sobre Java", "Editora Z", null, null, null);
        livroDAO.adicionar(livro1);
        livroDAO.adicionar(livro2);
        livroDAO.adicionar(livro3);

        // Criar e adicionar comentários
        Comentario comentario1 = new Comentario(new java.util.Date(), livro1, "Livro muito bom!", usuario1);
        Comentario comentario2 = new Comentario(new java.util.Date(), livro1, "Excelente", usuario2);
        Comentario comentario3 = new Comentario(new java.util.Date(), livro2, "Ótimo para desenvolvedores.", usuario1);

        comentarioDAO.adicionar(comentario1);
        comentarioDAO.adicionar(comentario2);
        comentarioDAO.adicionar(comentario3);

        // Associar os comentários aos usuários
        usuario1.getComentarios().add(comentario1);
        usuario1.getComentarios().add(comentario3);
        usuario2.getComentarios().add(comentario2);

        // Associar os comentários aos livros
        livro1.getComentarios().add(comentario1);
        livro1.getComentarios().add(comentario2);
        livro2.getComentarios().add(comentario3);

        // Listar livros e seus comentários
        List<Livro> livros = livroDAO.listarTodos();
        System.out.println("Livros e Comentários:");
        for (Livro livro : livros) {
            System.out.println("Livro: " + livro.getTitulo() + " (" + livro.getAutor() + ")");
            for (Comentario comentario : livro.getComentarios()) {
                System.out.println("  - Comentário: " + comentario.getTexto() + " [Usuário: " + comentario.getUsuario().getNome() + "]");
            }
        }

        // Listar usuários e seus comentários
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        System.out.println("\nUsuários e Comentários:");
        for (Usuario usuario : usuarios) {
            System.out.println("Usuário: " + usuario.getNome());
            for (Comentario comentario : usuario.getComentarios()) {
                System.out.println("  - Comentário: " + comentario.getTexto() + " [Livro: " + comentario.getLivro().getTitulo() + "]");
            }
        }

        // Deletar livro
        livroDAO.deletar(livro3.getId());
        System.out.println("Livros restantes:");
        for (Livro livro : livroDAO.listarTodos()) {
            System.out.println(livro.getTitulo());
        }
    }
}