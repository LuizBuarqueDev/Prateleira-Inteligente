package com.prateleira_inteligente.mappers;

import com.prateleira_inteligente.dto.AutorDTO;
import com.prateleira_inteligente.entities.Autor;
import com.prateleira_inteligente.services.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AutorMapper implements IMapper<Autor, AutorDTO> {

    private final AutorService autorService;

    @Override
    public Autor toEntity(AutorDTO autorDTO) {
        Autor autor = new Autor();
        if (autorDTO.getIdLivros() != null) {
            autor.setNome(autorDTO.getNome());
            autor.setLivros(autorService.getById(autor.getId()).getLivros());
        }
        return autor;
    }

    @Override
    public AutorDTO toDTO(Autor autor) {
        return AutorDTO.builder()
                .id(autor.getId())
                .nome(autor.getNome())
                .idLivros(autor.getLivros() != null ? autor.getLivros()
                        .stream()
                        .map(l -> l.getId()).collect(Collectors.toList()) : null)
                .build();
    }
}
