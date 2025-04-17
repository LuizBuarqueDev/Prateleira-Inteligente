package com.prateleira_inteligente.mappers;

import com.prateleira_inteligente.dto.LivroDTO;
import com.prateleira_inteligente.entities.Categoria;
import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.entities.Usuario;
import com.prateleira_inteligente.entities.UsuarioLivro;
import com.prateleira_inteligente.services.AutorService;
import com.prateleira_inteligente.services.CategoriaService;
import com.prateleira_inteligente.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LivroMapper implements IMapper<Livro, LivroDTO> {

    private final AutorService autorService;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;

    @Override
    public LivroDTO toDTO(Livro livro) {
        return LivroDTO.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .anoPublicacao(livro.getAnoPublicacao())
                .descricao(livro.getDescricao())
                .editora(livro.getEditora())
                .idAutor(getAutorId(livro))
                .idCategorias(getCategoriaIds(livro))
                .idUsuarios(getUsuarioIds(livro))
                .build();
    }

    @Override
    public Livro toEntity(LivroDTO dto) {
        Livro livro = new Livro();
        livro.setId(dto.getId());
        livro.setTitulo(dto.getTitulo());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setDescricao(dto.getDescricao());
        livro.setEditora(dto.getEditora());

        if (dto.getIdAutor() != null) {
            livro.setAutor(autorService.getById(dto.getIdAutor()));
        }

        if (dto.getIdCategorias() != null) {
            List<Categoria> categorias = dto.getIdCategorias().stream()
                    .map(categoriaService::getById)
                    .collect(Collectors.toList());
            livro.setCategorias(categorias);
        }

        if (dto.getIdUsuarios() != null) {
            List<Usuario> usuarios = dto.getIdUsuarios().stream()
                    .map(usuarioService::getById)
                    .collect(Collectors.toList());
            livro.setUsuariosLivros(mapUsuariosParaUsuarioLivro(usuarios, livro));
        }

        return livro;
    }

    private Long getAutorId(Livro livro) {
        return livro.getAutor() != null ? livro.getAutor().getId() : null;
    }

    private List<Long> getCategoriaIds(Livro livro) {
        return livro.getCategorias() != null
                ? livro.getCategorias().stream()
                .map(Categoria::getId)
                .collect(Collectors.toList())
                : null;
    }

    private List<Long> getUsuarioIds(Livro livro) {
        return livro.getUsuariosLivros() != null
                ? livro.getUsuariosLivros().stream()
                .map(usuarioLivro -> usuarioLivro.getUsuario().getId())
                .collect(Collectors.toList())
                : null;
    }

    private List<UsuarioLivro> mapUsuariosParaUsuarioLivro(List<Usuario> usuarios, Livro livro) {
        return usuarios.stream()
                .map(usuario -> {
                    UsuarioLivro usuarioLivro = new UsuarioLivro();
                    usuarioLivro.setUsuario(usuario);
                    usuarioLivro.setLivro(livro);
                    return usuarioLivro;
                })
                .collect(Collectors.toList());
    }
}