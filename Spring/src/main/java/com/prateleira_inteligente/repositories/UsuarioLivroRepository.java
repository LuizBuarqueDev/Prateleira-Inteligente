package com.prateleira_inteligente.repositories;

import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.entities.Usuario;
import com.prateleira_inteligente.entities.UsuarioLivro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UsuarioLivroRepository extends JpaRepository<UsuarioLivro, Long> {
    Optional<UsuarioLivro> findByUsuarioAndLivro(Usuario usuario, Livro livro);
    List<UsuarioLivro> findByUsuario(Usuario usuario);
    void deleteByUsuarioAndLivro(Usuario usuario, Livro livro);

    List<UsuarioLivro> findByLivro(Livro livro);
}