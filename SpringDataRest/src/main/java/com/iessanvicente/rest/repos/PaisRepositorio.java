package com.iessanvicente.rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.iessanvicente.rest.models.Pais;

@RepositoryRestResource(exported = true)
public interface PaisRepositorio extends JpaRepository<Pais, Long> {

}
