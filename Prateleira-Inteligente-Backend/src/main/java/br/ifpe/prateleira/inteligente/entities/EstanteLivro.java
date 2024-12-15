package br.ifpe.prateleira.inteligente.entities;

import javax.persistence.*;

@Entity
@Table(name = "Estante_Livro")
public class EstanteLivro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idEstante")
    private Estante estante;

    @ManyToOne
    @JoinColumn(name = "idLivro")
    private Livro livro;


    public EstanteLivro() {}

    public EstanteLivro(Estante estante, Livro livro) {
        this.estante = estante;
        this.livro = livro;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estante getEstante() {
        return estante;
    }

    public void setEstante(Estante estante) {
        this.estante = estante;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}