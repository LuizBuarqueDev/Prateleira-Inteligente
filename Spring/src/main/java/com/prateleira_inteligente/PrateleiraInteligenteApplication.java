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
//        mock.mocking();
    }
}