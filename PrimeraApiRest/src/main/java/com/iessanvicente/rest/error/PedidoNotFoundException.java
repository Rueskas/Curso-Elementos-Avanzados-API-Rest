package com.iessanvicente.rest.error;

public class PedidoNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8426657256592385740L;

	public PedidoNotFoundException(Long id) {
		super("No encontrado el pedido con id: " + id);
	}
	
	public PedidoNotFoundException() {
		super("No se ha encontrado ningun pedido");
	}
}
