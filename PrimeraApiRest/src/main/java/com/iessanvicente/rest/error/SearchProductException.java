package com.iessanvicente.rest.error;

public class SearchProductException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1343613513129723605L;

	public SearchProductException(String search) {
		super("No busqueda de \'" + search + "\' no produjo ningun resultado.");
	}
	
	public SearchProductException() {
		super("La busqueda no produjo ningun resultado.");
	}
}
