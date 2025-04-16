package com.prateleira_inteligente.controllers;

import com.prateleira_inteligente.dto.AvaliacaoDTO;
import com.prateleira_inteligente.entities.Avaliacao;
import com.prateleira_inteligente.mappers.IMapper;
import com.prateleira_inteligente.services.IService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/avaliacao")
@RequiredArgsConstructor
public class AvaliacaoController implements IController<AvaliacaoDTO, Long> {

    private final IService<Avaliacao> service;
    private final IMapper<Avaliacao, AvaliacaoDTO> mapper;

    @PostMapping("/create")
    @Override
    public ResponseEntity<AvaliacaoDTO> create(@RequestBody AvaliacaoDTO dto) {
        Avaliacao entity = service.save(mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<AvaliacaoDTO> findById(@PathVariable Long id) {
        Avaliacao entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<AvaliacaoDTO> update(@PathVariable Long id, @RequestBody AvaliacaoDTO dto) {
        Avaliacao updatedEntity = service.update(id, mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDTO(updatedEntity));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findByIds")
    @Override
    public ResponseEntity<List<AvaliacaoDTO>> findAllByIds(@RequestParam List<Long> ids) {
        List<AvaliacaoDTO> dtos = service.findAllById(ids)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<AvaliacaoDTO>> findAll() {
        List<AvaliacaoDTO> dtos = service.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}