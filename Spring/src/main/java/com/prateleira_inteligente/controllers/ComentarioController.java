package com.prateleira_inteligente.controllers;

import com.prateleira_inteligente.dto.ComentarioDTO;
import com.prateleira_inteligente.entities.Comentario;
import com.prateleira_inteligente.mappers.ComentarioMapper;
import com.prateleira_inteligente.services.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/comentario")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService service;
    private final ComentarioMapper mapper;

    @PostMapping("/criar")
    public ResponseEntity<ComentarioDTO> create(@RequestBody ComentarioDTO dto) {
        Comentario entity = service.save(mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDTO(entity));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDTO(service.getById(id)));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ComentarioDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscarPorIDs")
    public ResponseEntity<List<ComentarioDTO>> findByIDs(@RequestBody List<Long> ids) {
        List<ComentarioDTO> dtos = service.findAllById(ids)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
