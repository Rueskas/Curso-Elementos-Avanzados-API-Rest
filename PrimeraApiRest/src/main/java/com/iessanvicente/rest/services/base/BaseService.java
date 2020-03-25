package com.iessanvicente.rest.services.base;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<T, ID, R extends JpaRepository<T, ID>> {

	@Autowired
	protected R repo;
	
	public T save(T t) {
		return repo.save(t);
	}
	
	public Optional<T> findById(ID id){
		return repo.findById(id);
	}

	public boolean existsById(ID id){
		return repo.existsById(id);
	}
	
	public List<T> findAll(){
		return repo.findAll();
	}
	
	public Page<T> findAll(Pageable pageable){
		return repo.findAll(pageable);
	}
	
	public T edit(T t) {
		return repo.save(t);
	}
	
	public void delete(T t) {
		repo.delete(t);
	}
	

	public void deleteById(ID id) {
		repo.deleteById(id);;
	}
}
