package com.prateleira_inteligente.repositories;

import com.prateleira_inteligente.entities.Prateleira;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrateleiraRepository extends JpaRepository<Prateleira, Long> {
    boolean existsByUsuarioIdAndLivroId(Long usuarioId, Long livroId);
    List<Prateleira> findByUsuarioId(Long usuarioId);
}