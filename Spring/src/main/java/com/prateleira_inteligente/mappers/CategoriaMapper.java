package com.prateleira_inteligente.mappers;

import com.prateleira_inteligente.dto.CategoriaDTO;
import com.prateleira_inteligente.entities.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper implements IMapper<Categoria, CategoriaDTO> {

    @Override
    public Categoria toEntity(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());
        categoria.setNome(dto.getNome());
        return categoria;
    }

    @Override
    public CategoriaDTO toDTO(Categoria categoria) {
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .build();
    }
}