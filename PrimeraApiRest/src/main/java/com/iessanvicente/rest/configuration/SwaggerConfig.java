package com.iessanvicente.rest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.iessanvicente.rest.controllers"))
				.paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
	}
	
	@Bean
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API Tienda")
				.description("API de ejemplo del Curso Desarrollo de un API REST con Spring Boot de OpenWebinars")
				.version("1.0")
				.contact(new Contact("Rueskas", " https://openwebinars.net/academia/aprende/api-rest-spring-boot/7237/", ""))
				.build();
				
	}
}
