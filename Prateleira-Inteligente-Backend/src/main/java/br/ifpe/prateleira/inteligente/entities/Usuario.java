package br.ifpe.prateleira.inteligente.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nome;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioLivro> usuarioLivros = new ArrayList<>();


    public Usuario() {}

    public Usuario(String nome, List<Comentario> comentarios, List<UsuarioLivro> usuarioLivros) {
        this.nome = nome;
        this.comentarios = comentarios;
        this.usuarioLivros = usuarioLivros;
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

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<UsuarioLivro> getUsuarioLivros() {
        return usuarioLivros;
    }

    public void setUsuarioLivros(List<UsuarioLivro> usuarioLivros) {
        this.usuarioLivros = usuarioLivros;
    }
}