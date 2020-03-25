package com.iessanvicente.rest.services;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.iessanvicente.rest.controllers.FicheroController;
import com.iessanvicente.rest.dto.CreateProductoDTO;
import com.iessanvicente.rest.models.Producto;
import com.iessanvicente.rest.repos.ProductoRepositorio;
import com.iessanvicente.rest.services.base.BaseService;
import com.iessanvicente.rest.upload.StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService extends BaseService<Producto, Long, ProductoRepositorio>{
	@Autowired
	private final CategoriaService categoriaServicio;
	@Autowired
	private final StorageService storageService;
	
	public Page<Producto> findByNombre(String name, Pageable pageable){
		return this.repo.findByNombreContainsIgnoreCase(name, pageable);
	}
	
	
	public Producto nuevoProducto(CreateProductoDTO nuevo, MultipartFile file) {
		String urlImagen = null;
				
		if (!file.isEmpty()) {
			String imagen = storageService.store(file);
			urlImagen = MvcUriComponentsBuilder
						.fromMethodName(FicheroController.class, "serveFile", imagen, null)  
						.build().toUriString();
		}
				
		
		// En ocasiones, no necesitamos el uso de ModelMapper si la conversión que vamos a hacer
		// es muy sencilla. Con el uso de @Builder sobre la clase en cuestión, podemos realizar 
		// una transformación rápida como esta.
		
		Producto nuevoProducto = Producto.builder()
				.nombre(nuevo.getNombre())
				.precio(nuevo.getPrecio())
				.imagen(urlImagen)
				.categoria(categoriaServicio.findById(nuevo.getCategoriaId()).orElse(null))
				.build();
		
		return this.save(nuevoProducto);
		
	}
	
	public Page<Producto> findByArgs(final Optional<String> nombre, final Optional<Float> precio, Pageable pageable){
		Specification<Producto> specNombreProducto = new Specification<Producto>() {

			@Override
			public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if(nombre.isPresent()) {
					return criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), '%'+nombre.get() + '%');
				} else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
				}
			}
		};
		
		Specification<Producto> specPrecioProducto = new Specification<Producto>() {

			@Override
			public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if(precio.isPresent()) {
					return criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precio.get());
				} else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
				}
			}
			
		};
		
		Specification<Producto> ambas = specNombreProducto.or(specPrecioProducto);
		
		return this.repo.findAll(ambas, pageable);
	}
	
	public Optional<Producto> findByIdConLotes(Long id){
		return this.repo.findByIdJoinFetch(id);
	}
}
