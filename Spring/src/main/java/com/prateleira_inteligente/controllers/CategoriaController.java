package com.prateleira_inteligente.controllers;

import com.prateleira_inteligente.dto.CategoriaDTO;
import com.prateleira_inteligente.entities.Categoria;
import com.prateleira_inteligente.mappers.CategoriaMapper;
import com.prateleira_inteligente.services.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;
    private final CategoriaMapper mapper;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<CategoriaDTO> dtos = service.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Long id) {
        Categoria entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }
}