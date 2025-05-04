package com.prateleira_inteligente.mappers;

import com.prateleira_inteligente.dto.PrateleiraDTO;
import com.prateleira_inteligente.entities.UsuarioLivro;
import org.springframework.stereotype.Component;

@Component
public class PrateleiraMapper implements IMapper<UsuarioLivro, PrateleiraDTO> {

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
        usuarioLivro.setObservacoes(dto.getObservacoes());
        usuarioLivro.setNota(dto.getNota());
        return usuarioLivro;
    }
}