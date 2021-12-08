package br.com.bernhoeft.meetings.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bernhoeft.meetings.domain.AbstractEntity;

public abstract class AbstractService<T extends AbstractEntity, R extends JpaRepository<T, Long>> {
	
	protected R repository;

	public AbstractService(R repository) {
		super();
		this.repository = repository;
	}
	
	public T save(T entity) {
		return repository.save(entity);
	}
	
	public T get(Long id) {
		return repository.findById(id).get();
	}
	
	public List<T> getAll(){
		return repository.findAll();
	}
	
	public T delete(Long id) {
		repository.deleteById(id);
		return null;
	}

}
