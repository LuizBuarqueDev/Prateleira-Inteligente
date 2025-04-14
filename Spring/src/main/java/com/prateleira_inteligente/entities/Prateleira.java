package com.prateleira_inteligente.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "prateleiras")
public class Prateleira extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    private LocalDateTime dataAdicao;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @OneToOne(mappedBy = "prateleira", cascade = CascadeType.ALL)
    private Avaliacao avaliacao;
}