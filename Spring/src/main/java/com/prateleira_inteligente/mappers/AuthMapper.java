package com.prateleira_inteligente.mappers;

import com.prateleira_inteligente.dto.AuthDTO;
import com.prateleira_inteligente.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper implements IMapper<Usuario, AuthDTO> {


    @Override
    public Usuario toEntity(AuthDTO authDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(authDTO.getNomeUsuario());
        usuario.setSenha(new BCryptPasswordEncoder().encode(authDTO.getSenha()));
        usuario.setRole(authDTO.getRole());
        return usuario;
    }

    @Override
    public AuthDTO toDTO(Usuario entity) {
        return null;
    }
}
