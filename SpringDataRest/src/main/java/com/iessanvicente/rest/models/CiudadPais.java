package com.iessanvicente.rest.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="ciudadPais", types= {Ciudad.class})
public interface CiudadPais {
	
	String getNombre();
	
	@Value("#{target.pais.nombre}")
	String getPais();

}
