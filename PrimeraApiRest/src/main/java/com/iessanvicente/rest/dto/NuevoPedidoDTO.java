package com.iessanvicente.rest.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NuevoPedidoDTO {
	private String nombre;
	private Set<NuevaLineaPedidoDTO> lineas;
}
