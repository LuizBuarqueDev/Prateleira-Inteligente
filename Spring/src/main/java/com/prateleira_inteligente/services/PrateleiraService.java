package com.prateleira_inteligente.services;

import com.prateleira_inteligente.dto.PrateleiraDTO;
import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.entities.Usuario;
import com.prateleira_inteligente.entities.UsuarioLivro;
import com.prateleira_inteligente.mappers.PrateleiraMapper;
import com.prateleira_inteligente.repositories.UsuarioLivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prateleira_inteligente.services.PrateleiraService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrateleiraService {

    private final UsuarioLivroRepository usuarioLivroRepository;
    private final UsuarioService usuarioService;
    private final LivroService livroService;
    private final PrateleiraMapper prateleiraMapper;

    @Transactional
    public PrateleiraDTO adicionarLivro(PrateleiraDTO dto) {
        Usuario usuario = usuarioService.getById(dto.getIdUsuario());
        Livro livro = livroService.getById(dto.getIdLivro());

        Optional<UsuarioLivro> existente = usuarioLivroRepository.findByUsuarioAndLivro(usuario, livro);

        UsuarioLivro usuarioLivro;
        if (existente.isPresent()) {
            usuarioLivro = existente.get();
            usuarioLivro.setNota(dto.getNota());
            usuarioLivro.setObservacoes(dto.getObservacoes());
        } else {
            usuarioLivro = prateleiraMapper.toEntity(dto);
            usuarioLivro.setUsuario(usuario);
            usuarioLivro.setLivro(livro);
        }

        return prateleiraMapper.toDTO(usuarioLivroRepository.save(usuarioLivro));
    }

    @Transactional
    public void removerLivro(Long idUsuario, Long idLivro) {
        Usuario usuario = usuarioService.getById(idUsuario);
        Livro livro = livroService.getById(idLivro);
        usuarioLivroRepository.deleteByUsuarioAndLivro(usuario, livro);
    }

    @Transactional(readOnly = true)
    public List<PrateleiraDTO> listarLivrosPorUsuario(Long idUsuario) {
        Usuario usuario = usuarioService.getById(idUsuario);
        return usuarioLivroRepository.findByUsuario(usuario)
                .stream()
                .map(prateleiraMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<PrateleiraDTO> buscarLivroNaPrateleira(Long idUsuario, Long idLivro) {
        Usuario usuario = usuarioService.getById(idUsuario);
        Livro livro = livroService.getById(idLivro);
        return usuarioLivroRepository.findByUsuarioAndLivro(usuario, livro)
                .map(prateleiraMapper::toDTO);
    }

    @Transactional
    public PrateleiraDTO avaliarLivro(Long idUsuario, Long idLivro, Double nota) {
        if (nota < 0 || nota > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 0 e 5");
        }

        Usuario usuario = usuarioService.getById(idUsuario);
        Livro livro = livroService.getById(idLivro);

        UsuarioLivro usuarioLivro = usuarioLivroRepository.findByUsuarioAndLivro(usuario, livro)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado na prateleira do usuário"));

        usuarioLivro.setNota(nota);
        usuarioLivro = usuarioLivroRepository.save(usuarioLivro);

        return prateleiraMapper.toDTO(usuarioLivro);
    }

    @Transactional(readOnly = true)
    public Double calcularMediaAvaliacoes(Long idLivro) {
        Livro livro = livroService.getById(idLivro);
        List<UsuarioLivro> usuarioLivros = usuarioLivroRepository.findByLivro(livro);

        return usuarioLivros.stream()
                .filter(ul -> ul.getNota() != null) // Filtra apenas os que têm avaliação
                .mapToDouble(UsuarioLivro::getNota)
                .average()
                .orElse(0.0);
    }
}