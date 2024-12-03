package br.ifpe.prateleira.inteligente.entities;

import javax.persistence.*;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idEstante")
    private Estante estante;

    // Construtor padrão (obrigatório para Hibernate)
    public Livro() {
    }

    // Getter e Setter para o ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
