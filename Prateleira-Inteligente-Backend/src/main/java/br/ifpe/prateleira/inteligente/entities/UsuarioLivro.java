package br.ifpe.prateleira.inteligente.entities;

import javax.persistence.*;

    @Entity
    @Table(name = "UsarioLivro")
public class UsuarioLivro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuarioLivro;

        @ManyToOne
        @JoinColumn(name = "idUsuario")
        private Usuario usuario;

        @ManyToOne
        @JoinColumn(name = "idLivro")
        private Livro livro;

        public UsuarioLivro() {
        }

        public UsuarioLivro(Usuario usuario, Livro livro) {
            this.usuario = usuario;
            this.livro = livro;
        }

        public Long getIdUsuarioLivro() {
            return idUsuarioLivro;
        }

        public void setIdUsuarioLivro(Long idUsuarioLivro) {
            this.idUsuarioLivro = idUsuarioLivro;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }

        public Livro getLivro() {
            return livro;
        }

        public void setLivro(Livro livro) {
            this.livro = livro;
        }
    }
