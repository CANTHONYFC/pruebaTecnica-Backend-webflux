package com.springboot.app.producto.models.impl;


import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;
import com.springboot.app.producto.models.dao.ProductoRepository;
import com.springboot.app.producto.models.dto.ProductoDTO;
import com.springboot.app.producto.models.entity.Producto;
import com.springboot.app.producto.models.service.ProductoService;
import com.springboot.app.producto.models.service.ProductoServiceProccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoDaoImpl implements ProductoServiceProccess {
    @Autowired
    private ProductoService productoServiceFeign;

    @Autowired
    private ProductoRepository productoDao;

    public Flux<ProductoDTO> listar() {
    return    productoDao.findAll().flatMap(producto -> {
                    ProductoDTO productoDTO = new ProductoDTO();
                    productoDTO.setId(producto.getId());
                    productoDTO.setNombre(producto.getNombre());
                    productoDTO.setPrecio(producto.getPrecio());
                    System.out.println("antes de feing");
                    return productoServiceFeign.findById(producto.getCreateUser())
                            .map(usuario -> {
                                usuario.setPassword(null);
                                usuario.setUsername(null);
                                productoDTO.setCreateUser(usuario);
                                return productoDTO;
                            });
                });
    }
    public Mono<ProductoDTO> get(Producto productoBean) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(productoBean.getId());
        productoDTO.setNombre(productoBean.getNombre());
        productoDTO.setPrecio(productoBean.getPrecio());

        return productoServiceFeign.findById(productoBean.getCreateUser())
                .map(usuario -> {
                    usuario.setPassword(null);
                    usuario.setUsername(null);
                    productoDTO.setCreateUser(usuario);
                    return productoDTO;
                })
                // Si ocurre algún error en la llamada al servicio Feign, retorna un Mono vacío
                .onErrorResume(throwable -> Mono.empty());

    }
}
