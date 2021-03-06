package com.iessanvicente.rest.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Categoria {
 
	@Id @GeneratedValue
	private long id;
	
	private String nombre;
}
