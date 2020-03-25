package com.iessanvicente.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineaPedidoDTO {
	private String nombreProducto;
	private int cantidad;
	private float precio;
}
