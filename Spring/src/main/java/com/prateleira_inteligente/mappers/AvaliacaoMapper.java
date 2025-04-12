package com.prateleira_inteligente.mappers;

import com.prateleira_inteligente.dto.AvaliacaoDTO;
import com.prateleira_inteligente.entities.Avaliacao;
import com.prateleira_inteligente.services.LivroService;
import com.prateleira_inteligente.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AvaliacaoMapper implements IMapper<Avaliacao, AvaliacaoDTO> {

    private final UsuarioService usuarioService;
    private final LivroService livroService;

    @Override
    public AvaliacaoDTO toDTO(Avaliacao avaliacao) {
        return AvaliacaoDTO.builder()
                .id(avaliacao.getId())
                .nota(avaliacao.getNota())
                .idLivro(avaliacao.getLivro().getId())
                .idUsuario(avaliacao.getUsuario().getId())
                .build();
    }

    @Override
    public Avaliacao toEntity(AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(avaliacaoDTO.getId());
        avaliacao.setNota(avaliacaoDTO.getNota());
        avaliacao.setLivro(livroService.getById(avaliacaoDTO.getIdLivro()));
        avaliacao.setUsuario(usuarioService.getById(avaliacaoDTO.getIdUsuario()));
        return avaliacao;
    }
}
