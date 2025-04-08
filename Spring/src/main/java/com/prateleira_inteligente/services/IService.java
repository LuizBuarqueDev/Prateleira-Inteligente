package com.prateleira_inteligente.services;

import java.util.List;

public interface IService<T> {
    T save(T t);
    T update(Long id ,T t);
    T getById(Long id);
    void delete(Long id);
    List<T> findAll();
    List<T> findAllById(List<Long> ids);
}