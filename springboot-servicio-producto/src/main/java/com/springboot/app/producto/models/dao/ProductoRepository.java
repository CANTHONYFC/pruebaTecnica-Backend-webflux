package com.springboot.app.producto.models.dao;

import com.springboot.app.producto.models.entity.Producto;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ProductoRepository extends R2dbcRepository<Producto, Long> {
}
