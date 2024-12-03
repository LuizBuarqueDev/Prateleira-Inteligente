package br.ifpe.prateleira.inteligente.entities;

import javax.persistence.*;

@Entity
@Table(name= "Categoria")
class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}