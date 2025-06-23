package com.prateleira_inteligente.dto.simplified;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioSimplificado {
    private String id;
    private String nome;
}
