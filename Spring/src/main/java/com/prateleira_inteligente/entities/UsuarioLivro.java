package com.prateleira_inteligente.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuario_livro")
public class UsuarioLivro extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    private LocalDateTime dataAdicao = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    private Double nota;
    public boolean temAvaliacao() {
        return nota != null;
    }
}