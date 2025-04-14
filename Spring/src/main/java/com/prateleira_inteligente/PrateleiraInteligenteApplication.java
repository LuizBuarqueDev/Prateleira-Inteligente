package com.prateleira_inteligente;

import com.prateleira_inteligente.entities.Avaliacao;
import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.entities.Usuario;
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
    @Transactional
    public void run(String... args) {
        Livro livro1 = new Livro();
        livro1.setTitulo("Java para Iniciantes");
        livro1.setAnoPublicacao(LocalDate.of(2020,1,1));
        livro1.setDescricao("Um guia completo para aprender Java.");
        livro1.setEditora("McGraw-Hill");

        livroService.save(livro1);

        Usuario usuario1 = new Usuario();
        usuario1.setNome("João Silva");

        usuarioService.save(usuario1);

        Avaliacao avaliacao1 = new Avaliacao();
        avaliacao1.setUsuario(usuario1);
        avaliacao1.setLivro(livro1);
        avaliacao1.setNota(10.0);

        avaliacaoService.save(avaliacao1);
    }
}