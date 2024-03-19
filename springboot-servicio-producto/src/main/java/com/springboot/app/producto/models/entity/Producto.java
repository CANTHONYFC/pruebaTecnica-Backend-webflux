package com.springboot.app.producto.models.entity;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("producto")
public class Producto {
	@Id
	private Long id;
	@Column("nombre")
	private String nombre;
	@Column("precio")
	private Double precio;
	@Column("create_user")
	private Long createUser;


}
