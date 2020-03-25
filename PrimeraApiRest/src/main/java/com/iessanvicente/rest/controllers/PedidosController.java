package com.iessanvicente.rest.controllers;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.iessanvicente.rest.converter.PedidoDTOConverter;
import com.iessanvicente.rest.dto.NuevoPedidoDTO;
import com.iessanvicente.rest.dto.PedidoDTO;
import com.iessanvicente.rest.error.PedidoNotFoundException;
import com.iessanvicente.rest.models.Pedido;
import com.iessanvicente.rest.services.PedidoServicio;
import com.iessanvicente.rest.util.pagination.PaginationLinksUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidosController {

	@Autowired
	private final PedidoServicio pedidoServicio;
	@Autowired
	private final PedidoDTOConverter dtoConverter;
	@Autowired
	private final PaginationLinksUtils paginationLinks;
	
	@GetMapping("/")
	public ResponseEntity<?> pedidos(
			@PageableDefault(size=10, page=0) Pageable pageable,
			HttpServletRequest request){
		Page<Pedido> pedidos = pedidoServicio.findAll(pageable);
		if(pedidos.isEmpty()) {
			throw new PedidoNotFoundException();
		} else {
			UriComponentsBuilder uriBuilder = 
					UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
			
			return ResponseEntity.ok()
					.header("link", paginationLinks.createLinkHeader(pedidos, uriBuilder))
					.body(pedidos.map(dtoConverter::convertToDto));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> pedido(@PathVariable Long id){
		Pedido pedido = pedidoServicio.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
		return ResponseEntity.ok(dtoConverter.convertToDto(pedido));
	}
	
	@PostMapping("/")
	public ResponseEntity<?> insertPedido(
			@RequestBody NuevoPedidoDTO nuevoPedido){
		Pedido p = 
				pedidoServicio.save(dtoConverter.convertToPedido(nuevoPedido));
		if(p != null) {
			return ResponseEntity.ok(dtoConverter.convertToDto(p));
		}
		return null;
	}
	
	@PutMapping("/{id}")
	public PedidoDTO putPedido(
			@PathVariable long id,
			@RequestBody NuevoPedidoDTO nuevoPedido){
		Pedido ped = pedidoServicio.findById(id).map(p ->{
			p.setId(id);
			p.setCliente(nuevoPedido.getNombre());
			p.addAllLineaPedido(nuevoPedido.getLineas().stream()
					.map(dtoConverter::convertToLineaPedido)
					.collect(Collectors.toSet()));
			pedidoServicio.edit(p);
			return p;
		}).orElseThrow(() -> new PedidoNotFoundException(id));
		PedidoDTO dto =  dtoConverter.convertToDto(ped);
		System.out.println("gi");
		return dto;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> putPedido(
				@PathVariable long id){
		pedidoServicio.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
