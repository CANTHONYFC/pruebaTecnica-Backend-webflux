package com.springboot.app.producto.clientes;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@FeignClient(name = "servicio-usuarios")
public interface ProductoClienteRest {

	@GetMapping("/usuarios/{id}")
	public Mono<Usuario> findById(@PathVariable Long id);



}
