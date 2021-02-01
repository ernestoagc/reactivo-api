package com.reactivo.apirest.handler;

import java.net.URI;
import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactivo.apirest.dao.ProductoDao;
import com.reactivo.apirest.modelo.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Component
public class ProductoHandler {

	

	@Autowired
	ProductoDao productoDao;
	
	public Mono<ServerResponse> listar(ServerRequest request){
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(productoDao.findAll(),Producto.class);
	}
	
	public Mono<ServerResponse> listar2(ServerRequest request){
		
		Flux<Producto> fluxProductos=productoDao.findAll();
		
		Flux<Long> retraso = Flux.interval(Duration.ofSeconds(2));
		Flux<Producto> resultado = fluxProductos.zipWith(retraso,(productos,re) ->productos);
		
		return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(fluxProductos.delayElements(Duration.ofSeconds(1)).log(),Producto.class);
	}
	
	public Mono<ServerResponse> crear(ServerRequest request){
		
		Mono<Producto> producto = request.bodyToMono(Producto.class);
		
		return producto.flatMap(p ->{
			if(p.getCreacion()==null) {
				p.setCreacion(new Date());
			}
			return productoDao.save(p);
			
		}).flatMap(p -> ServerResponse
				.created(URI.create("/producto/".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(p)));
		
		//return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(productoDao.findAll(),Producto.class);
	}
	
}
