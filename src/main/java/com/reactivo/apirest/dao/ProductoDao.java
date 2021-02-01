package com.reactivo.apirest.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.reactivo.apirest.modelo.Producto;

public interface ProductoDao extends ReactiveMongoRepository<Producto, String> {

}
