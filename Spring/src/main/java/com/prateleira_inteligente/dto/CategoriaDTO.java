package com.prateleira_inteligente.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoriaDTO {
    private Long id;
    private String nome;
}