package com.springboot.app.producto.models.service;

import com.springboot.app.producto.models.dto.ProductoDTO;
import com.springboot.app.producto.models.entity.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoServiceProccess {
    public Flux<ProductoDTO> listar();
    public Mono<ProductoDTO> get(Producto producto);


}
