package com.prateleira_inteligente.dto;

import com.prateleira_inteligente.config.security.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioDTO {
    private String id;
    private String nome;
    private String senha;
    private Role role;
}
