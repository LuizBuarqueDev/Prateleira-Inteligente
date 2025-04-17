package com.prateleira_inteligente.controllers;

import com.prateleira_inteligente.dto.LivroDTO;
import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.mappers.IMapper;
import com.prateleira_inteligente.services.IService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livro")
@RequiredArgsConstructor
public class LivroController implements IController<LivroDTO, Long> {

    private final IService<Livro> service;
    private final IMapper<Livro, LivroDTO> mapper;

    @PostMapping("/create")
    @Override
    public ResponseEntity<LivroDTO> create(@RequestBody LivroDTO dto) {
        Livro entity = service.save(mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<LivroDTO> findById(@PathVariable Long id) {
        Livro entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<LivroDTO> update(@PathVariable Long id, @RequestBody LivroDTO dto) {
        Livro updatedEntity = service.update(id, mapper.toEntity(dto));
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
    public ResponseEntity<List<LivroDTO>> findAllByIds(@RequestParam List<Long> ids) {
        List<LivroDTO> dtos = service.findAllById(ids)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<LivroDTO>> findAll() {
        List<LivroDTO> dtos = service.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}