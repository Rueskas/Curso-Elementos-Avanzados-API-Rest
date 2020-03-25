package com.iessanvicente.rest.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iessanvicente.rest.dto.CreateLoteDTO;
import com.iessanvicente.rest.error.LoteCreateException;
import com.iessanvicente.rest.models.Lote;
import com.iessanvicente.rest.repos.LoteRepositorio;
import com.iessanvicente.rest.services.base.BaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoteService extends BaseService<Lote, Long, LoteRepositorio> {

	private final ProductoService productoServicio;
	
	@Override
	public Optional<Lote> findById(Long id) {
		return repo.findByJoinFetch(id);
	}

	public Lote nuevoLote(CreateLoteDTO nuevoLote) {
		
		Lote l = Lote.builder()
					.nombre(nuevoLote.getNombre())
					.build();
		
		nuevoLote.getProductos().stream()
			.map(id -> {
				return productoServicio.findByIdConLotes(id).orElseThrow(() -> new LoteCreateException());
			})
			.forEach(l::addProducto);
			
		return save(l);
		
	}
	
	

}