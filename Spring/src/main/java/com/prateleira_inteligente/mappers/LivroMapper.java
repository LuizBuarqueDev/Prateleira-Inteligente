package com.prateleira_inteligente.mappers;

import com.prateleira_inteligente.dto.LivroDTO;
import com.prateleira_inteligente.dto.AutorDTO;
import com.prateleira_inteligente.entities.Categoria;
import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.entities.Usuario;
import com.prateleira_inteligente.entities.UsuarioLivro;
import com.prateleira_inteligente.services.CategoriaService;
import com.prateleira_inteligente.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LivroMapper implements IMapper<Livro, LivroDTO> {

    private final AutorMapper autorMapper;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;

    @Override
    public LivroDTO toDTO(Livro livro) {
        return LivroDTO.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .capa(livro.getCapa())
                .anoPublicacao(livro.getAnoPublicacao())
                .descricao(livro.getDescricao())
                .editora(livro.getEditora())
                .autor(livro.getAutor() != null ? autorMapper.toDTO(livro.getAutor()) : null)
                .idCategorias(livro.getCategorias() != null ? livro.getCategorias()
                        .stream()
                        .map(Categoria::getId)
                        .collect(Collectors.toList()) : null)
                .nomesCategorias(livro.getCategorias() != null ? livro.getCategorias()
                        .stream()
                        .map(Categoria::getNome)
                        .collect(Collectors.toList()) : null)
                .idUsuarios(livro.getUsuariosLivros() != null ? livro.getUsuariosLivros()
                        .stream()
                        .map(ul -> ul.getUsuario().getId())
                        .collect(Collectors.toList()) : null)
                .build();
    }

    @Override
    public Livro toEntity(LivroDTO dto) {
        Livro livro = new Livro();

        livro.setId(dto.getId());
        livro.setTitulo(dto.getTitulo());
        livro.setCapa(dto.getCapa());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setDescricao(dto.getDescricao());
        livro.setEditora(dto.getEditora());

        if (dto.getAutor() != null) {
            livro.setAutor(autorMapper.toEntity(dto.getAutor()));
        }

        if (dto.getIdCategorias() != null) {
            livro.setCategorias(categoriaService.findAllById(dto.getIdCategorias()));
        }

        if (dto.getIdUsuarios() != null) {
            List<Usuario> usuarios = usuarioService.findAllById(dto.getIdUsuarios());
            livro.setUsuariosLivros(mapUsuariosParaUsuarioLivro(usuarios, livro));
        }

        return livro;
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