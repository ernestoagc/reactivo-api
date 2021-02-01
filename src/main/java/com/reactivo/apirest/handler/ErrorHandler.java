package com.reactivo.apirest.handler;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class ErrorHandler extends AbstractErrorWebExceptionHandler {
	
	

	public ErrorHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
			ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
		super(errorAttributes, resourceProperties, applicationContext);
		super.setMessageReaders(configurer.getReaders());
		super.setMessageWriters(configurer.getWriters());
		// TODO Auto-generated constructor stub
	}


	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		// TODO Auto-generated method stub
		return RouterFunctions.route(RequestPredicates.all(),this::handleError);
	}

	
	private Mono<ServerResponse> handleError(ServerRequest req){
		Map<String,Object> error=this.getErrorAttributes(req, false);
		Mono<ServerResponse> res = ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR )
		.body(BodyInserters.fromObject(error.get("message")));
		return res;
	}
	
}
