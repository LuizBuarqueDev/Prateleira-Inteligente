package com.prateleira_inteligente.controllers;

import com.prateleira_inteligente.dto.AutorDTO;
import com.prateleira_inteligente.entities.Autor;
import com.prateleira_inteligente.mappers.AutorMapper;
import com.prateleira_inteligente.services.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/autor")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping("/criar")
    public ResponseEntity<AutorDTO> create(@RequestBody AutorDTO dto) {
        Autor entity = service.save(mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> findById(@PathVariable Long id) {
        Autor entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AutorDTO> update(@PathVariable Long id, @RequestBody AutorDTO dto) {
        Autor updatedEntity = service.update(id, mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDTO(updatedEntity));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/encontrarPorID")
    public ResponseEntity<List<AutorDTO>> findAllByIds(@RequestParam List<Long> ids) {
        List<AutorDTO> dtos = service.findAllById(ids)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> findAll() {
        List<AutorDTO> dtos = service.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
