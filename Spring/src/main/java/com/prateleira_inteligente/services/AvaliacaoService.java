package com.prateleira_inteligente.services;

import com.prateleira_inteligente.models.Avaliacao;

import java.util.List;

public class AvaliacaoService implements IService<Avaliacao>{

    @Override
    public Avaliacao save(Avaliacao avaliacao) {
        return null;
    }

    @Override
    public Avaliacao update(Long id, Avaliacao avaliacao) {
        return null;
    }

    @Override
    public Avaliacao getById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Avaliacao> findAll() {
        return List.of();
    }

    @Override
    public List<Avaliacao> findAllById(List<Long> ids) {
        return List.of();
    }
}
