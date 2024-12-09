package br.ifpe.prateleira.inteligente.main;

import br.ifpe.prateleira.inteligente.persistence.UsuarioDAO;
import br.ifpe.prateleira.inteligente.entities.Estante;
import br.ifpe.prateleira.inteligente.entities.Usuario;

public class Main2 {

    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Estante estante = new Estante("Estante de Livros");

        Usuario usuario = new Usuario(estante, "Maria");
        usuarioDAO.adicionar(usuario);

        Long usuarioId = usuario.getIdUsuario();
        Usuario usuarioBuscado = usuarioDAO.buscarPorId(usuarioId);

        if (usuarioBuscado != null) {
            System.out.println("Usuário encontrado: " + usuarioBuscado.getNome());
        } else {
            System.out.println("Usuário não encontrado!");
        }

        UsuarioDAO.fechar();
    }
}
