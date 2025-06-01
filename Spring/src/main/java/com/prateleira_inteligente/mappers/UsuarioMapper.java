package com.prateleira_inteligente.mappers;

import com.prateleira_inteligente.dto.UsuarioDTO;
import com.prateleira_inteligente.entities.Usuario;
import com.prateleira_inteligente.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper implements IMapper<Usuario, UsuarioDTO> {

    private PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    @Override
    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        return usuario;
    }

    @Override
    public UsuarioDTO toDTO(Usuario entity) {
        return null;
    }
}
