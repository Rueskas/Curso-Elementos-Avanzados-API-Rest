package com.iessanvicente.rest.models;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="ciudadSinUbicacion", types= {Ciudad.class})
public interface CiudadProj {
	String getNombre();
}
