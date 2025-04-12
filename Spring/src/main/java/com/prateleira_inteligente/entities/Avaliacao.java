package com.prateleira_inteligente.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Avaliacoes", uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "livro_id"})})
public class Avaliacao extends  BaseEntity {

    @Column(nullable = false)
    private Double nota;

    @ManyToOne
    @JoinColumn(name = "usuario_id",nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;
}