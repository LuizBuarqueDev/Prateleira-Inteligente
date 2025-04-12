package com.prateleira_inteligente.services;

import com.prateleira_inteligente.entities.Autor;
import com.prateleira_inteligente.repositories.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorService implements IService<Autor> {

    private final AutorRepository autorRepository;
    private final LivroService livroService;

    @Override
    @Transactional
    public Autor save(Autor autor) {
        return null;
    }

    @Override
    @Transactional
    public Autor update(Long id, Autor autor) {
        //TODO: Consertar, quando um livro e atualizado os autores que não foram atualizados perdem seus livros.
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Autor getById(Long id) {
        return null;
    }

    @Transactional
    @Override
    public void delete(Long id) {
    }


    @Override
    @Transactional(readOnly = true)
    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Autor> findAllById(List<Long> ids) {
        return autorRepository.findAllById(ids);
    }
}