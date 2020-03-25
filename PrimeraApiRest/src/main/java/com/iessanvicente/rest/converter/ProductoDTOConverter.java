package com.iessanvicente.rest.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.iessanvicente.rest.dto.ProductoDTO;
import com.iessanvicente.rest.models.Producto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor 
public class ProductoDTOConverter {

	private final ModelMapper modelMapper;
	
	public ProductoDTO convertToDto(Producto producto) {
		return modelMapper.map(producto, ProductoDTO.class);
	}
	
	public ProductoDTO convertToProductoDto(Producto producto) {
		return ProductoDTO.builder()
				.nombre(producto.getNombre())
				.imagen(producto.getImagen())
				.precio(producto.getPrecio())
				.categoria(producto.getCategoria() != null ? 
						producto.getCategoria().getNombre():null)
				.build();
	}
}
