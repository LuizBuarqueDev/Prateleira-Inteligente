package com.prateleira_inteligente.mappers;

import com.prateleira_inteligente.config.security.Role;
import com.prateleira_inteligente.dto.UsuarioDTO;
import com.prateleira_inteligente.dto.simplified.UsuarioSimplificado;
import com.prateleira_inteligente.entities.Usuario;
import com.prateleira_inteligente.util.HashId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper implements IMapper<Usuario, UsuarioDTO> {

    private PasswordEncoder passwordEncoder;
    private final HashId hashId;

    @Override
    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(hashId.decode(usuarioDTO.getId()));
        usuario.setNome(usuarioDTO.getNome());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuario.setRole(usuarioDTO.getRole() != null ? usuarioDTO.getRole() : Role.USER);
        return usuario;
    }

    @Override
    public UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(hashId.encode(usuario.getId()))
                .nome(usuario.getNome())
                .role(usuario.getRole())
                .build();
    }

    public UsuarioSimplificado toSimplifiedUser(Usuario usuario) {
        return UsuarioSimplificado.builder()
                .id(hashId.encode(usuario.getId()))
                .nome(usuario.getNome())
                .build();
    }
}
