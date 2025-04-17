package com.prateleira_inteligente;

import com.prateleira_inteligente.entities.Avaliacao;
import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.entities.Usuario;
import com.prateleira_inteligente.entities.UsuarioLivro;
import com.prateleira_inteligente.services.AvaliacaoService;
import com.prateleira_inteligente.services.LivroService;
import com.prateleira_inteligente.services.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@RequiredArgsConstructor
@SpringBootApplication
public class PrateleiraInteligenteApplication implements CommandLineRunner {

    private final LivroService livroService;
    private final UsuarioService usuarioService;
    private final AvaliacaoService avaliacaoService;

    public static void main(String[] args) {
        SpringApplication.run(PrateleiraInteligenteApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Livro livro1 = new Livro();
        livro1.setTitulo("Java para Iniciantes");
        livro1.setAnoPublicacao(LocalDate.of(2020,1,1));
        livro1.setDescricao("Um guia completo para aprender Java.");
        livro1.setEditora("McGraw-Hill");

        livroService.save(livro1); // salva sem relacionamento ainda

        Usuario usuario1 = new Usuario();
        usuario1.setNome("João Silva");

        usuarioService.save(usuario1); // salva o usuário

        // Agora criamos o relacionamento:
        UsuarioLivro usuarioLivro = new UsuarioLivro();
        usuarioLivro.setUsuario(usuario1);
        usuarioLivro.setLivro(livro1);

        // Adiciona o relacionamento nas duas pontas (opcional, mas bom pra consistência)
        livro1.getUsuariosLivros().add(usuarioLivro);
        usuario1.getUsuarioLivros().add(usuarioLivro);

        // Re-salva o livro com o relacionamento (se Cascade.ALL ou Cascade.PERSIST estiver no Livro -> UsuarioLivro)
        livroService.save(livro1);
    }
}