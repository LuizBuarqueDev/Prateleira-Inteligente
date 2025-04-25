package com.prateleira_inteligente.services;

import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.entities.Usuario;
import com.prateleira_inteligente.entities.UsuarioLivro;
import com.prateleira_inteligente.repositories.UsuarioLivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrateleiraService {

    private final UsuarioLivroRepository usuarioLivroRepository;
    private final UsuarioService usuarioService;
    private final LivroService livroService;

    @Transactional
    public UsuarioLivro adicionarLivroNaPrateleira(Long idUsuario, Long idLivro, Double nota, String observacoes) {
        Usuario usuario = usuarioService.getById(idUsuario);
        Livro livro = livroService.getById(idLivro);

        Optional<UsuarioLivro> existente = usuarioLivroRepository.findByUsuarioAndLivro(usuario, livro);

        if (existente.isPresent()) {
            UsuarioLivro usuarioLivro = existente.get();
            usuarioLivro.setNota(nota);
            usuarioLivro.setObservacoes(observacoes);
            return usuarioLivroRepository.save(usuarioLivro);
        }

        UsuarioLivro usuarioLivro = new UsuarioLivro();
        usuarioLivro.setUsuario(usuario);
        usuarioLivro.setLivro(livro);
        usuarioLivro.setNota(nota);
        usuarioLivro.setObservacoes(observacoes);

        return usuarioLivroRepository.save(usuarioLivro);
    }

    @Transactional
    public void removerLivroDaPrateleira(Long idUsuario, Long idLivro) {
        Usuario usuario = usuarioService.getById(idUsuario);
        Livro livro = livroService.getById(idLivro);

        usuarioLivroRepository.deleteByUsuarioAndLivro(usuario, livro);
    }

    @Transactional(readOnly = true)
    public List<UsuarioLivro> listarLivrosNaPrateleira(Long idUsuario) {
        Usuario usuario = usuarioService.getById(idUsuario);
        return usuarioLivroRepository.findByUsuario(usuario);
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioLivro> buscarLivroNaPrateleira(Long idUsuario, Long idLivro) {
        Usuario usuario = usuarioService.getById(idUsuario);
        Livro livro = livroService.getById(idLivro);
        return usuarioLivroRepository.findByUsuarioAndLivro(usuario, livro);
    }
}