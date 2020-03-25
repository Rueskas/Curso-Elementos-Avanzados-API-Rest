package com.iessanvicente.rest.converter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iessanvicente.rest.dto.LineaPedidoDTO;
import com.iessanvicente.rest.dto.NuevaLineaPedidoDTO;
import com.iessanvicente.rest.dto.NuevoPedidoDTO;
import com.iessanvicente.rest.dto.PedidoDTO;
import com.iessanvicente.rest.error.ProductoNotFoundException;
import com.iessanvicente.rest.models.LineaPedido;
import com.iessanvicente.rest.models.Pedido;
import com.iessanvicente.rest.models.Producto;
import com.iessanvicente.rest.services.ProductoService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor 
public class PedidoDTOConverter {
	
	@Autowired
	private final ProductoService productoService;
	
	public PedidoDTO convertToDto(Pedido pedido) {
		PedidoDTO dto =  PedidoDTO.builder()
				.cliente(pedido.getCliente())
				.fecha(pedido.getFecha())
				.build();
		dto.setLineas(pedido.getLineas() != null ? 
						pedido.getLineas().stream()
						.map(this::convertToDto)
						.collect(Collectors.toSet()): new HashSet<>());
		System.out.println("Golla");
		return dto;
	}
	
	public LineaPedidoDTO convertToDto(LineaPedido linea) {
		return LineaPedidoDTO.builder()
				.cantidad(linea.getCantidad())
				.precio(linea.getPrecio())
				.nombreProducto(linea.getProducto().getNombre())
				.build();
	}
	
	public Pedido convertToPedido(NuevoPedidoDTO nuevoPedido) {
		Pedido pedido = Pedido.builder()
				.cliente(nuevoPedido.getNombre())
				.build();
		Set<LineaPedido> lineas = nuevoPedido.getLineas() != null ?
				nuevoPedido.getLineas().stream()
					.map(this::convertToLineaPedido)
					.collect(Collectors.toSet()) : new HashSet<>();
		pedido.addAllLineaPedido(lineas);
		return pedido;
	}
	
	public LineaPedido convertToLineaPedido(NuevaLineaPedidoDTO nuevaLinea) {
		Producto p = productoService.findById(nuevaLinea.getProductoId())
				.orElseThrow( () ->
						new ProductoNotFoundException(nuevaLinea.getProductoId()));
		return LineaPedido.builder()
				.producto(p)
				.cantidad(nuevaLinea.getCantidad())
				.precio(p.getPrecio())
				.build();
	}
}
