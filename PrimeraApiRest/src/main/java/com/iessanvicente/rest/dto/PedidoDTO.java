package com.iessanvicente.rest.dto;

import java.time.LocalDate;
import java.util.Set;

import com.iessanvicente.rest.models.LineaPedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
	private String cliente;
	private LocalDate fecha;
	private Set<LineaPedidoDTO> lineas;
}
