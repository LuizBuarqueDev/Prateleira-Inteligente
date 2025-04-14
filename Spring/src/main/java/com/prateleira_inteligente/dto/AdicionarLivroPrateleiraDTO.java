package com.prateleira_inteligente.dto;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.*;

@Data
@Builder
public class AdicionarLivroPrateleiraDTO {
    @NotNull(message = "ID do livro é obrigatório")
    private Long livroId;

    // ver com Luis se essas propriedades era para estar em avaliação ou aqui
    @Min(0) @Max(5)
    private Double nota;

    private String observacoes;
}
