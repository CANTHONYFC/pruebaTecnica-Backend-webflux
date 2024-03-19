package com.springboot.app.producto.controllers;

import com.springboot.app.producto.models.dao.ProductoRepository;
import com.springboot.app.producto.models.dto.ProductoDTO;
import com.springboot.app.producto.models.entity.Producto;
import com.springboot.app.producto.models.service.ProductoServiceProccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RefreshScope
@RestController
@RequestMapping("/crud")
public class ProductoController {
	
	private static Logger log = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private Environment env;


	@Autowired
	private ProductoServiceProccess productoServiceProccess;

	private final ProductoRepository productoRepository;

	public ProductoController(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<ProductoDTO>> getProductoById(@PathVariable Long id) {
		return productoRepository.findById(id)
				.flatMap(savedProducto -> productoServiceProccess.get(savedProducto) // Obtén el producto guardado
						.map(savedProductoDTO -> ResponseEntity.status(HttpStatus.CREATED).body(savedProductoDTO)) // Crea la respuesta con el producto obtenido
				);
	}

	@GetMapping("/listar")
	public Flux<ProductoDTO> getAllProducto() {

		return  productoServiceProccess.listar();
	}

	@PostMapping("/create")
	public Mono<ResponseEntity<ProductoDTO>> createProducto(@RequestBody Producto producto,@RequestHeader("id_user") String userId) {
		producto.setCreateUser(Long.valueOf(userId));
    return productoRepository.save(producto) // Guarda el producto
				.flatMap(savedProducto -> productoServiceProccess.get(savedProducto) // Obtén el producto guardado
						.map(savedProductoDTO -> ResponseEntity.status(HttpStatus.CREATED).body(savedProductoDTO)) // Crea la respuesta con el producto obtenido
				);
	}


	@PutMapping("/update/{id}")
	public Mono<ResponseEntity<ProductoDTO>> updateProducto(@PathVariable Long id, @RequestBody Producto producto,@RequestHeader("id_user") String userId) {
		return productoRepository.findById(id) // Busca el producto por ID
				.flatMap(existingProducto -> {
					// Actualiza los campos del producto existente con los valores del producto recibido
					existingProducto.setNombre(producto.getNombre());
					existingProducto.setPrecio(producto.getPrecio());
					existingProducto.setCreateUser(Long.valueOf(userId));
					// Guarda el producto actualizado
					return productoRepository.save(existingProducto)
							.flatMap(savedProducto -> productoServiceProccess.get(savedProducto)
									.map(savedProductoDTO -> ResponseEntity.ok().body(savedProductoDTO))
							);
				})
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build())); // Maneja el caso en que no se encuentre el producto
	}


	@DeleteMapping("delete/{id}")
	public Mono<ResponseEntity<Void>> deleteProducto(@PathVariable Long id) {
		return productoRepository.deleteById(id)
				.then(Mono.just(ResponseEntity.ok().<Void>build()))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}



}
