package com.prateleira_inteligente.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AutorDTO {

    private Long id;
    private String nome;
    private List<Long> idLivros;
}
