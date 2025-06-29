package com.prateleira_inteligente.repositories;

import com.prateleira_inteligente.entities.Comentario;
import com.prateleira_inteligente.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByLivroId(Long livroId);

    List<Comentario> findByLivro(Livro livro);
}
