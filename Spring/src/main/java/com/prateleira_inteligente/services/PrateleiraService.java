package com.prateleira_inteligente.services;

import com.prateleira_inteligente.dto.AdicionarLivroPrateleiraDTO;
import com.prateleira_inteligente.entities.*;
import com.prateleira_inteligente.exception.ConflictException;
import com.prateleira_inteligente.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrateleiraService {
    
    private final PrateleiraRepository prateleiraRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    
    @Transactional
    public Prateleira adicionarLivro(Usuario usuario, AdicionarLivroPrateleiraDTO dto) {
        Livro livro = livroRepository.findById(dto.getLivroId())
                .orElseThrow(() -> new NotFoundException("Livro não encontrado"));
        
        if (prateleiraRepository.existsByUsuarioIdAndLivroId(usuario.getId(), dto.getLivroId())) {
            throw new ConflictException("Livro já está na prateleira");
        }
        
        Prateleira prateleira = new Prateleira();
        prateleira.setUsuario(usuario);
        prateleira.setLivro(livro);
        prateleira.setDataAdicao(LocalDateTime.now());
        
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setNota(dto.getNota());
        avaliacao.setPrateleira(prateleira);
        
        prateleira.setAvaliacao(avaliacao);
        
        return prateleiraRepository.save(prateleira);
    }
    
    @Transactional
    public void removerLivro(Long usuarioId, Long livroId) {
        Prateleira prateleira = prateleiraRepository.findByUsuarioIdAndLivroId(usuarioId, livroId)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado na prateleira"));
        
        prateleiraRepository.delete(prateleira);
    }
}