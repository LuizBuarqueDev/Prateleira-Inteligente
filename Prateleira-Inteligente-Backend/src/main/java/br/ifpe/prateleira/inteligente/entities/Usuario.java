package br.ifpe.prateleira.inteligente.entities;

import javax.persistence.*;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nome;

    @OneToOne
    @JoinColumn(name = "idEstante", unique = true)
    private Estante estante;


    public Usuario() {}

    public Usuario(Estante estante, String nome) {
        this.estante = estante;
        this.nome = nome;
    }

    public Estante getEstante() {
        return estante;
    }

    public void setEstante(Estante estante) {
        this.estante = estante;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
