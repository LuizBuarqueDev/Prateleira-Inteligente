package com.prateleira_inteligente.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class LivroDTO {
    private Long id;
    private String titulo;
    private String capa;
    private LocalDate anoPublicacao;
    private String descricao;
    private String editora;
    private AutorDTO autor;
    private List<Long> idCategorias;
    private List<String> nomesCategorias;
    private List<Long> idUsuarios;
    private List<Long> idComentarios;
    private Double mediaAvaliacoes;
}