package com.prateleira_inteligente.repositories;

import com.prateleira_inteligente.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro,Long> {

    @Query("SELECT DISTINCT l FROM Livro l " +
            "LEFT JOIN l.categorias c " +
            "LEFT JOIN l.autor a " +
            "WHERE LOWER(l.titulo) LIKE %:termo% " +
            "OR LOWER(c.nome) LIKE %:termo% " +
            "OR LOWER(a.nome) LIKE %:termo%")
    List<Livro> findByTermo(String termo);
}
