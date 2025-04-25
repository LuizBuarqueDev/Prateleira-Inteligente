package com.prateleira_inteligente.mappers;

import com.prateleira_inteligente.dto.PrateleiraDTO;
import com.prateleira_inteligente.entities.UsuarioLivro;
import com.prateleira_inteligente.services.LivroService;
import com.prateleira_inteligente.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrateleiraMapper implements IMapper<UsuarioLivro, PrateleiraDTO> {

    private final UsuarioService usuarioService;
    private final LivroService livroService;

    @Override
    public PrateleiraDTO toDTO(UsuarioLivro usuarioLivro) {
        return PrateleiraDTO.builder()
                .id(usuarioLivro.getId())
                .idUsuario(usuarioLivro.getUsuario().getId())
                .idLivro(usuarioLivro.getLivro().getId())
                .observacoes(usuarioLivro.getObservacoes())
                .nota(usuarioLivro.getNota())
                .build();
    }

    @Override
    public UsuarioLivro toEntity(PrateleiraDTO dto) {
        UsuarioLivro usuarioLivro = new UsuarioLivro();
        usuarioLivro.setId(dto.getId());
        usuarioLivro.setUsuario(usuarioService.getById(dto.getIdUsuario()));
        usuarioLivro.setLivro(livroService.getById(dto.getIdLivro()));
        usuarioLivro.setObservacoes(dto.getObservacoes());
        usuarioLivro.setNota(dto.getNota());
        return usuarioLivro;
    }
}