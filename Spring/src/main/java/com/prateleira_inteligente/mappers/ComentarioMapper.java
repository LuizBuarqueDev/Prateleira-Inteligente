package com.prateleira_inteligente.mappers;

import com.prateleira_inteligente.dto.ComentarioDTO;
import com.prateleira_inteligente.entities.Comentario;
import com.prateleira_inteligente.services.LivroService;
import com.prateleira_inteligente.services.UsuarioService;
import com.prateleira_inteligente.util.HashId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComentarioMapper implements IMapper<Comentario, ComentarioDTO> {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final LivroService livroService;

    @Override
    public Comentario toEntity(ComentarioDTO comentarioDTO) {
        Comentario comentario = new Comentario();
        comentario.setUsuario(usuarioService.getById(comentarioDTO.getUsuario().getId()));
        comentario.setTexto(comentarioDTO.getTexto());
        comentario.setLivro(livroService.getById(comentarioDTO.getIdLivro()));
        comentario.setDataCriacao(comentarioDTO.getDataCriacao());
        return comentario;
    }

    @Override
    public ComentarioDTO toDTO(Comentario entity) {
        return ComentarioDTO.builder()
                .usuario(usuarioMapper.toSimplifiedUser(entity.getUsuario()))
                .texto(entity.getTexto())
                .dataCriacao(entity.getDataCriacao())
                .build();
    }
}
