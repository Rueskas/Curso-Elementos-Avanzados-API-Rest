package com.iessanvicente.rest.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="ciudadLatLng", types= {Ciudad.class})
public interface CiudadLatLng {

	@Value("#{target.id}")
	long getId();
	
	String getNombre();
	
	@Value("#{target.lat}#{', '}#{target.lng}")
	String getCoordenadas();
	
	@Value("#{target.pais.nombre}")
	String getPais();
}
