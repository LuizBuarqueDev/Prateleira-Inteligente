package com.prateleira_inteligente.mappers;

import com.prateleira_inteligente.dto.PrateleiraDTO;
import com.prateleira_inteligente.entities.Prateleira;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrateleiraMapper implements IMapper<Prateleira, PrateleiraDTO> {

    @Override
    public PrateleiraDTO toDTO(Prateleira prateleira) {
        return PrateleiraDTO.builder()
                .id(prateleira.getId())
                .livroId(prateleira.getLivro().getId())
                .tituloLivro(prateleira.getLivro().getTitulo())
                .nota(prateleira.getAvaliacao() != null ? prateleira.getAvaliacao().getNota() : null)
                .dataAdicao(prateleira.getDataAdicao())
                .observacoes(prateleira.getObservacoes())
                .build();
    }

    @Override
    public Prateleira toEntity(PrateleiraDTO dto) {

    }
}