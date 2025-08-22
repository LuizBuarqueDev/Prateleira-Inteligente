package com.prateleira_inteligente.controllers;

import com.prateleira_inteligente.dto.LivroDTO;
import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.mappers.LivroMapper;
import com.prateleira_inteligente.services.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livro")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping("/create")
    public ResponseEntity<LivroDTO> create(@RequestBody LivroDTO dto) {
        Livro entity = service.save(mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> findById(@PathVariable Long id) {
        Livro entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LivroDTO> update(@PathVariable Long id, @RequestBody LivroDTO dto) {
        Livro updatedEntity = service.update(id, mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDTO(updatedEntity));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findByIds")
    public ResponseEntity<List<LivroDTO>> findAllByIds(@RequestParam List<Long> ids) {
        List<LivroDTO> dtos = service.findAllById(ids)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> findAll() {
        List<LivroDTO> dtos = service.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<LivroDTO>> buscar (@RequestParam String busca) {
    List<LivroDTO> dtos = service.buscar(busca)
            .stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<LivroDTO>> findByCategoriaId(@PathVariable Long categoriaId) {
        List<LivroDTO> dtos = service.findByCategoriaId(categoriaId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/filter/minRating/{minRating}/sortBy/{sortBy}")
    public ResponseEntity<List<LivroDTO>> filterLivros(
            @PathVariable Double minRating,
            @PathVariable String sortBy) {

        List<Livro> livros = service.filterLivros(minRating, sortBy);
        List<LivroDTO> dtos = livros.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/similar/{idLivro}")
    public ResponseEntity<List<LivroDTO>> findSimilarBooks(@PathVariable Long idLivro) {
        List<LivroDTO> dtos = service.findSimilarBooks(idLivro)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}