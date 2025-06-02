package com.prateleira_inteligente.dto;

import com.prateleira_inteligente.config.security.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDTO {
    String nomeUsuario;
    String senha;
    Role role;
}
