package com.prateleira_inteligente.services;


import com.prateleira_inteligente.entities.Livro;
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
    public Livro update(Long id, Livro livro) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Livro getById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
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
