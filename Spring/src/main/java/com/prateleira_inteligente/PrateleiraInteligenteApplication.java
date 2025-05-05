package com.prateleira_inteligente;


import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.entities.Usuario;
import com.prateleira_inteligente.services.LivroService;
import com.prateleira_inteligente.services.UsuarioService;
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
    private final Mock mock;


    public static void main(String[] args) {
        SpringApplication.run(PrateleiraInteligenteApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Livro livro1 = new Livro();
        livro1.setTitulo("Java para Iniciantes");
        livro1.setAnoPublicacao(LocalDate.of(2020,1,1));
        livro1.setDescricao("Um guia completo para aprender Java.");
        livro1.setCapa("https://wallpapers.com/images/hd/weird-profile-pictures-idfvjaormuvjp8fy.jpg");
        livro1.setEditora("McGraw-Hill");

        livroService.save(livro1);

        Usuario usuario1 = new Usuario();
        usuario1.setNome("João Silva");

        usuarioService.save(usuario1);
        /*
        UsuarioLivro usuarioLivro = new UsuarioLivro();
        usuarioLivro.setUsuario(usuario1);
        usuarioLivro.setLivro(livro1);


        livro1.getUsuariosLivros().add(usuarioLivro);
        usuario1.getUsuarioLivros().add(usuarioLivro);
*/

        livroService.save(livro1);

        mock.mocking();
    }
}