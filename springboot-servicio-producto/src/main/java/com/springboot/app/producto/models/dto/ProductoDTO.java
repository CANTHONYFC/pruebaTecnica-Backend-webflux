package com.springboot.app.producto.models.dto;
import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;


public class ProductoDTO {
    private Long id;
    private String nombre;
    private Double precio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Usuario getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Usuario createUser) {
        this.createUser = createUser;
    }

    private Usuario createUser;


}
