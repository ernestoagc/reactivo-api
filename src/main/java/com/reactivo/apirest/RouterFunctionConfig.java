package com.reactivo.apirest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


import com.reactivo.apirest.handler.ProductoHandler;


import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterFunctionConfig {

	@Bean
	public RouterFunction<ServerResponse> routes(ProductoHandler handler){		
		return RouterFunctions.route(RequestPredicates.GET("/producto/listar"),
				request -> handler.listar(request)).
				andRoute(RequestPredicates.POST("/producto/"), handler::crear)
				.andRoute(RequestPredicates.GET("/producto/listar2"), handler::listar2);
		
	}
}
