package com.iessanvicente.rest.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;
import com.iessanvicente.rest.converter.ProductoDTOConverter;
import com.iessanvicente.rest.dto.CreateProductoDTO;
import com.iessanvicente.rest.dto.EditProductoDTO;
import com.iessanvicente.rest.dto.ProductoDTO;
import com.iessanvicente.rest.error.ApiError;
import com.iessanvicente.rest.error.ProductoNotFoundException;
import com.iessanvicente.rest.error.SearchProductException;
import com.iessanvicente.rest.models.Categoria;
import com.iessanvicente.rest.models.Producto;
import com.iessanvicente.rest.services.CategoriaService;
import com.iessanvicente.rest.services.ProductoService;
import com.iessanvicente.rest.upload.StorageService;
import com.iessanvicente.rest.util.pagination.PaginationLinksUtils;
import com.iessanvicente.rest.views.ProductoViews;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class ProductoController {
	
	@Autowired
	private final ProductoDTOConverter dtoConverter;
	@Autowired
	private final ProductoService productoServicio;
	@Autowired
	private CategoriaService categoriaServicio;
	@Autowired
	private PaginationLinksUtils paginationLinks;
	@Autowired
	private StorageService storageService;

	/**
	 * Obtenemos todos los productos
	 * 
	 * @return
	 */
	/*
	@CrossOrigin(origins="http://localhost:9001")
	@GetMapping("/producto")
	public ResponseEntity<?> obtenerTodos(
			@PageableDefault(size=10, page=0) Pageable pageable,
			HttpServletRequest request) {
		Page<Producto> products = productoServicio.findAll(pageable);
		if(products.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe ningun producto");
		} else {
			Page<ProductoDTO> result = products
					.map(dtoConverter::convertToDto);
			UriComponentsBuilder uriBuilder = 
					UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
			
			return ResponseEntity.ok()
					.header("link", paginationLinks.createLinkHeader(result, uriBuilder))
					.body(result);
		}
	}
	
	@GetMapping(value="/producto", params="nombre")
	public ResponseEntity<?> buscarPorNombre(
			@RequestParam(name="nombre") String nombre,
			@PageableDefault(size=10, page=0) Pageable pageable,
			HttpServletRequest request) {
		Page<Producto> products = productoServicio.findByNombre(nombre, pageable);
		if(products.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe ningun producto");
		} else {
			Page<ProductoDTO> result = products
					.map(dtoConverter::convertToDto);
			UriComponentsBuilder uriBuilder = 
					UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
			
			return ResponseEntity.ok()
					.header("link", paginationLinks.createLinkHeader(result, uriBuilder))
					.body(result);
		}
	}
*/
	
	@JsonView(ProductoViews.Dto.class)
	@GetMapping("/producto")
	public ResponseEntity<?> buscarProducto(
			@PageableDefault(size=10, page=0) Pageable pageable,
			@RequestParam(name="nombre") Optional<String> nombre,
			@RequestParam(name="precio") Optional<Float> precio,
			HttpServletRequest request){
		Page<Producto> page = productoServicio.findByArgs(nombre, precio, pageable);
		if(page.isEmpty()) {
			throw new SearchProductException();
		} else {
			Page<ProductoDTO> result = page
					.map(dtoConverter::convertToProductoDto);
			UriComponentsBuilder uriBuilder = 
					UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
			
			return ResponseEntity.ok()
					.header("link", paginationLinks.createLinkHeader(result, uriBuilder))
					.body(result);
		}
	}
		
	/**
	 * Obtenemos un producto en base a su ID
	 * 
	 * @param id
	 * @return Null si no encuentra el producto
	 */
	@JsonView(ProductoViews.DtoConPrecio.class)
	@ApiOperation(value="Obtener un producto por su ID", notes="Provee un mecanismo para obtener todos los datos de un producto mediante su ID")
	@ApiResponses(value = {
			@ApiResponse(code=200, message="OK", response=Producto.class),
			@ApiResponse(code=404, message="NOT FOUND", response=ApiError.class),
			@ApiResponse(code=500, message="Internal Server Error", response=ApiError.class)
	})
	@GetMapping("/producto/{id}")
	public ProductoDTO obtenerUno(@ApiParam(value="ID del producto", required=true, type="long") @PathVariable Long id) {
		/*
		Producto p = productoRepositorio.findById(id).orElse(null);
		if(p == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(dtoConverter.convertToDto(p));
		}
		*/
		return dtoConverter.convertToProductoDto(productoServicio.findById(id).orElseThrow(() -> new ProductoNotFoundException(id)));
	}

	/**
	 * Insertamos un nuevo producto
	 * 
	 * @param nuevo
	 * @return producto insertado
	 */
	@PostMapping(value = "/producto", 
			consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProductoDTO nuevoProducto(
			@RequestPart("nuevo") CreateProductoDTO nuevo,
			@RequestPart("file") MultipartFile file ) {
		
		String urlImage = null;
		
		if(!file.isEmpty()) {
			urlImage = storageService.store(file);
			urlImage = MvcUriComponentsBuilder
					.fromMethodName(FicheroController.class, "serveFile", urlImage, null)
					.build().toUriString();
		}
		if(categoriaServicio.findById(nuevo.getCategoriaId()).orElse(null) != null) {
			Categoria c = categoriaServicio.findById(nuevo.getCategoriaId()).orElse(null);
			
			Producto p = new Producto(null, nuevo.getNombre(), nuevo.getPrecio(), urlImage, c, null);
			Producto result = productoServicio.save(p);
			if(result != null) {
				return dtoConverter.convertToProductoDto(result);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la categoria");
		}
		return null;
	}

	/**
	 * 
	 * @param editar
	 * @param id
	 * @return
	 */
	@PutMapping("/producto/{id}")
	public EditProductoDTO editarProducto(@RequestBody EditProductoDTO editar, @PathVariable Long id) {
		return productoServicio.findById(id).map(p -> {
			p.setNombre(editar.getNombre());
			p.setPrecio(editar.getPrecio());
			productoServicio.save(p);
			return editar;
		}).orElseThrow(() ->  new ProductoNotFoundException(id));
	}

	/**
	 * Borra un producto del catálogo en base a su id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/producto/{id}")
	public ResponseEntity<?> borrarProducto(@PathVariable Long id) {
		Producto p = productoServicio.findById(id).orElseThrow(() -> new ProductoNotFoundException(id));
		productoServicio.deleteById(id);
		return ResponseEntity.noContent().build();
	}


}
