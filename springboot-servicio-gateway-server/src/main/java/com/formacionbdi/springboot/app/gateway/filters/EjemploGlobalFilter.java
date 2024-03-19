package com.formacionbdi.springboot.app.gateway.filters;

import java.util.Base64;
import java.util.Optional;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
// import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Component
public class EjemploGlobalFilter implements GlobalFilter, Ordered{

	@Value("${config.security.oauth.jwt.key}")
	private String llaveJwt;

	private final Logger logger = LoggerFactory.getLogger(EjemploGlobalFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("ejecutando filtro pre");
		String token = exchange.getRequest()
				.getHeaders()
				.getFirst(HttpHeaders.AUTHORIZATION);
		logger.info("token"+token);
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7); // Eliminar los primeros 7 caracteres ("Bearer ")
			logger.info("Token: {}", token);
			SecretKey llave = Keys.hmacShaKeyFor(Base64.getEncoder().encode(llaveJwt.getBytes()));
			Claims claims =Jwts.parserBuilder().setSigningKey(llave).build().parseClaimsJws(token).getBody();
			Integer id_user = claims.get("id", Integer.class);
			logger.info("id_user"+id_user);
			exchange.getRequest().mutate().headers(h -> h.add("id_user",id_user.toString()));

		}
	    return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			logger.info("ejecutando filtro post");

			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(valor -> {
				exchange.getResponse().getHeaders().add("token", valor);
			});

			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "rojo").build());
			// exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
		}));
	}

	@Override
	public int getOrder() {
		return 10;
	}

}
