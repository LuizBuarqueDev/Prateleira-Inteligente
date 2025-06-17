package com.prateleira_inteligente.repositories;

import com.prateleira_inteligente.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository <Autor, Long> {
    Autor findByNome(String nome);
}
