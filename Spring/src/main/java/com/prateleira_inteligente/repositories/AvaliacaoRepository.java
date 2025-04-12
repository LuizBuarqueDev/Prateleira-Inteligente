package com.prateleira_inteligente.repositories;

import com.prateleira_inteligente.entities.Avaliacao;
import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    Optional<Avaliacao> findByUsuarioAndLivro(Usuario usuario, Livro livro);
}
