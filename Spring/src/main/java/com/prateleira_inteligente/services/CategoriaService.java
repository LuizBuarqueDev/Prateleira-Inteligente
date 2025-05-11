package com.prateleira_inteligente.services;

import com.prateleira_inteligente.entities.Categoria;
import com.prateleira_inteligente.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService implements IService<Categoria> {
    
    private final CategoriaRepository categoriaRepository ;

    @Override
    @Transactional
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    @Transactional
    public Categoria update(Long id, Categoria categoria) {
        //TODO: Implementar o metodo update
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria getById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> findAllById(List<Long> ids) {
        return categoriaRepository.findAllById(ids);
    }

    public Categoria findByNome(String nome) {
        return categoriaRepository.findByNome(nome);
    }

}
