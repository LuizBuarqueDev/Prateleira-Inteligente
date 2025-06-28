package com.prateleira_inteligente.dto;

import com.prateleira_inteligente.dto.simplified.UsuarioSimplificado;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ComentarioDTO {
    private Long id;
    private UsuarioSimplificado usuario;
    private String texto;
    private Long idLivro;
    private LocalDateTime dataCriacao;
}
