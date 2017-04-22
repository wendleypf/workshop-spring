package br.edu.ufcg.embedded.util;

import java.util.List;

public interface Crud<T> {
    T create(T t);

    List<T> getAll();

    T getById(Long id);

    T update(T t);

    boolean removeById(Long id);

    boolean removeAll();
}
