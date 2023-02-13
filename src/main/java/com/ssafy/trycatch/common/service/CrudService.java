package com.ssafy.trycatch.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class CrudService<T, ID, R extends JpaRepository<T, ID>> {

    protected final R repository;

    public CrudService(R repository) {
        this.repository = repository;
    }

    public T register(T entity) {
        return repository.save(entity);
    }

    public <S extends T> List<S> registerAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public void flush() {
        repository.flush();
    }

    public <S extends T> S registerAndFlush(S entity) {
        return repository.saveAndFlush(entity);
    }

    public <S extends T> List<S> registerAllAndFlush(Iterable<S> entities) {
        return repository.saveAllAndFlush(entities);
    }

    public Optional<T> findById(ID id) { return repository.findById(id); }

    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public <S extends T> List<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    public List<T> findAllById(Iterable<ID> ids) {
        return repository.findAllById(ids);
    }

    public T getReferenceById(ID id) {
        return repository.getReferenceById(id);
    }

    public long count() {
        return repository.count();
    }

    public void removeById(ID id) {
        repository.deleteById(id);
    }

    public void remove(T entity) {
        repository.delete(entity);
    }

    public void removeAllById(Iterable<? extends ID> ids) {
        repository.deleteAllById(ids);
    }

    public void removeAll(Iterable<? extends T> entities) {
        repository.deleteAll(entities);
    }

    public void removeAllInBatch(Iterable<T> entities) {
        repository.deleteAllInBatch(entities);
    }

    public void removeAllByIdInBatch(Iterable<ID> ids) {
        repository.deleteAllByIdInBatch(ids);
    }

    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    public void removeAll() {
        repository.deleteAll();
    }
}
