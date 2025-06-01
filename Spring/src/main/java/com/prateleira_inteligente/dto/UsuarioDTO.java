package com.prateleira_inteligente.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioDTO {
    private String id;
    private String nome;
    private String role;
}
