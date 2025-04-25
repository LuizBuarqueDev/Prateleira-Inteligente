package com.prateleira_inteligente.controllers;

import com.prateleira_inteligente.dto.PrateleiraDTO;
import com.prateleira_inteligente.mappers.PrateleiraMapper;
import com.prateleira_inteligente.services.PrateleiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prateleira")
@RequiredArgsConstructor
public class PrateleiraController {

    private final PrateleiraService prateleiraService;
    private final PrateleiraMapper prateleiraMapper;

    @PostMapping("/adicionar")
    public ResponseEntity<PrateleiraDTO> adicionarLivro(
            @RequestParam Long idUsuario,
            @RequestParam Long idLivro,
            @RequestParam(required = false) Double nota,
            @RequestParam(required = false) String observacoes) {

        var usuarioLivro = prateleiraService.adicionarLivroNaPrateleira(idUsuario, idLivro, nota, observacoes);
        return ResponseEntity.ok(prateleiraMapper.toDTO(usuarioLivro));
    }

    @DeleteMapping("/remover")
    public ResponseEntity<Void> removerLivro(
            @RequestParam Long idUsuario,
            @RequestParam Long idLivro) {

        prateleiraService.removerLivroDaPrateleira(idUsuario, idLivro);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar/{idUsuario}")
    public ResponseEntity<List<PrateleiraDTO>> listarLivrosNaPrateleira(@PathVariable Long idUsuario) {
        var livros = prateleiraService.listarLivrosNaPrateleira(idUsuario)
                .stream()
                .map(prateleiraMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/buscar")
    public ResponseEntity<PrateleiraDTO> buscarLivroNaPrateleira(
            @RequestParam Long idUsuario,
            @RequestParam Long idLivro) {

        var usuarioLivro = prateleiraService.buscarLivroNaPrateleira(idUsuario, idLivro);
        return usuarioLivro.map(ul -> ResponseEntity.ok(prateleiraMapper.toDTO(ul)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}