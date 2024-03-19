package com.springboot.app.producto.models.service;
import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@Service
public class ProductoServiceFeign implements ProductoService {

	@Autowired
	private WebClient.Builder client;
	@Override
	public Mono<Usuario> findById(Long id) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id",id);
		return client.build().get().uri("/usuarios/{id}",params).accept
				(MediaType.APPLICATION_JSON_UTF8).
				exchange().
				flatMap(clientResponse -> clientResponse.bodyToMono(
						Usuario.class));

	}
}
