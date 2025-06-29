package com.prateleira_inteligente.repositories;

import com.prateleira_inteligente.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
     UserDetails findByNome(String nome);
}
