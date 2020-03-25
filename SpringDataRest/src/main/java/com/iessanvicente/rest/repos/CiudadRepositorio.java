package com.iessanvicente.rest.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.iessanvicente.rest.models.Ciudad;
import com.iessanvicente.rest.models.CiudadPais;

@RepositoryRestResource(path = "ciudades", collectionResourceRel = "ciudades",
	excerptProjection = CiudadPais.class)
public interface CiudadRepositorio extends JpaRepository<Ciudad, Long> {
	
	@RestResource(path="nombreComienzaPor", rel="nombreComienzaPor")
	Page<Ciudad> findByNombreStartsWithIgnoreCase(@Param(value="nombre") String nombre, Pageable pageable);

	@RestResource(path="nombreContiene", rel="nombreContiene")
	Page<Ciudad> findByNombreContainsIgnoreCase(@Param(value="nombre") String nombre, Pageable pageable);

}
