package com.springboot.app.producto.models.service;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;
import reactor.core.publisher.Mono;


public interface ProductoService {
	public Mono<Usuario> findById(Long id);
}
