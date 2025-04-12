package com.prateleira_inteligente.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Comentarios")
public class Comentario extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @Column(length = 1000)
    private String texto;

    private LocalDateTime dataCriacao = LocalDateTime.now();
}
