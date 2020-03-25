package com.iessanvicente.rest.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iessanvicente.rest.models.Lote;

@Repository
public interface LoteRepositorio extends JpaRepository<Lote, Long>{

	 @Query("select l from Lote l JOIN FETCH l.productos WHERE l.id = :id")
	 public Optional<Lote> findByJoinFetch(Long id);
}
