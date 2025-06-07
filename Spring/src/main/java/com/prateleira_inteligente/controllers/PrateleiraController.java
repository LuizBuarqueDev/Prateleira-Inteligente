package com.prateleira_inteligente.controllers;

import com.prateleira_inteligente.dto.PrateleiraDTO;
import com.prateleira_inteligente.services.PrateleiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/prateleira")
@RequiredArgsConstructor
public class PrateleiraController {

    private final PrateleiraService prateleiraService;

    @DeleteMapping("/{idUsuario}/{idLivro}")
    public ResponseEntity<Void> removerLivro(
            @PathVariable Long idUsuario,
            @PathVariable Long idLivro) {
        prateleiraService.removerLivro(idUsuario, idLivro);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PrateleiraDTO>> listarLivrosNaPrateleira(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(prateleiraService.listarLivrosPorUsuario(idUsuario));
    }

    @GetMapping("/{idUsuario}/{idLivro}")
    public ResponseEntity<PrateleiraDTO> buscarLivroNaPrateleira(
            @PathVariable Long idUsuario,
            @PathVariable Long idLivro) {
        return prateleiraService.buscarLivroNaPrateleira(idUsuario, idLivro)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<PrateleiraDTO> adicionarLivro(@RequestBody PrateleiraDTO dto) {

        return ResponseEntity.ok(prateleiraService.adicionarLivro(dto));
    }
}
