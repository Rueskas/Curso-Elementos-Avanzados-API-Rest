package com.iessanvicente.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.iessanvicente.rest.error.CategoriaNotFoundException;
import com.iessanvicente.rest.models.Categoria;
import com.iessanvicente.rest.services.CategoriaService;


@RestController
public class CategoriaController {

	@Autowired
	CategoriaService categoriaServicio;
	
	@GetMapping("/categoria")
	public List<Categoria> categorias(){
		List<Categoria> categorias = categoriaServicio.findAll();
		if(categorias.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe ninguna categoria");
		} else {
			return categorias;
		}
	}
	
	@GetMapping("/categoria/{id}")
	public Categoria categoria(@PathVariable long id) {
		return categoriaServicio.findById(id)
				.orElseThrow(() -> new CategoriaNotFoundException(id));
	}
	
	@PostMapping("/categoria")
	public Categoria newCategoria(@RequestBody Categoria categoria) {
		return categoriaServicio.save(categoria);
	}
	
	@PutMapping("/categoria/{id}")
	public Categoria putCategoria(@RequestBody Categoria categoria, @PathVariable long id) {
		if(categoriaServicio.existsById(id)) {
			categoria.setId(id);
			return categoriaServicio.save(categoria);
		} else {
			throw new CategoriaNotFoundException(id);
		}
	}
	
	@DeleteMapping("/categoria/{id}")
	public ResponseEntity<?> deleteCategoria(@PathVariable long id) {
		if(categoriaServicio.existsById(id)) {
			categoriaServicio.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			throw new CategoriaNotFoundException(id);
		}
	}
}
