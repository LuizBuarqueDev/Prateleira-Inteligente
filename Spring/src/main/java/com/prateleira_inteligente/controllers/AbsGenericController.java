package com.prateleira_inteligente.controllers;

import com.prateleira_inteligente.mappers.IMapper;
import com.prateleira_inteligente.services.IService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbsGenericController<T, DTO> implements IController<DTO, Long> {

    protected final IService<T> service;
    protected final IMapper<T, DTO> mapper;

    @PostMapping("/create")
    @Override
    public ResponseEntity<DTO> create(@RequestBody DTO dto) {
        T entity = service.save(mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<DTO> findById(@PathVariable Long id) {
        T entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<DTO> update(@PathVariable Long id, @RequestBody DTO dto) {
        T updatedEntity = service.update(id, mapper.toEntity(dto));
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
    public ResponseEntity<List<DTO>> findAllByIds(@RequestParam List<Long> ids) {
        List<DTO> dtos = service.findAllById(ids)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<DTO>> findAll() {
        List<DTO> dtos = service.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}