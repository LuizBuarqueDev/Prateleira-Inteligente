package com.prateleira_inteligente.services;


import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.entities.UsuarioLivro;
import com.prateleira_inteligente.repositories.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LivroService implements IService<Livro> {

    private final LivroRepository livroRepository;
    private final ComentarioService comentarioService;


    @Override
    @Transactional
    public Livro save(Livro livro) {
        return livroRepository.save(livro);
    }

    @Override
    @Transactional
    //TODO: Implementar o update
    public Livro update(Long id, Livro livroAtualizado) {
        Livro livroExistente = getById(id);

        // Atualiza campos básicos
        livroExistente.setTitulo(livroAtualizado.getTitulo());
        livroExistente.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
        livroExistente.setDescricao(livroAtualizado.getDescricao());
        livroExistente.setEditora(livroAtualizado.getEditora());

        // Atualiza o autor
        livroExistente.setAutor(livroAtualizado.getAutor());

        // Atualiza categorias (remove antigas e adiciona novas)
        livroExistente.getCategorias().clear();
        if (livroAtualizado.getCategorias() != null) {
            livroExistente.getCategorias().addAll(livroAtualizado.getCategorias());
        }

        // Atualiza usuariosLivros
        livroExistente.getUsuariosLivros().clear();
        if (livroAtualizado.getUsuariosLivros() != null) {
            for (UsuarioLivro usuarioLivro : livroAtualizado.getUsuariosLivros()) {
                usuarioLivro.setLivro(livroExistente); // garante referência reversa correta
                livroExistente.getUsuariosLivros().add(usuarioLivro);
            }
        }

        return livroRepository.save(livroExistente);
    }

    @Override
    @Transactional(readOnly = true)
    public Livro getById(Long id) {
        return livroRepository.findById(id).orElseThrow(()-> new RuntimeException("Livro não encontrado: " + id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        livroRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> findAll() {
        return livroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> findAllById(List<Long> ids) {
        return livroRepository.findAllById(ids);
    }
}
