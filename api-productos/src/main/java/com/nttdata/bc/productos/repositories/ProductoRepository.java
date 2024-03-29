package com.nttdata.bc.productos.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bc.productos.models.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveMongoRepository<Producto, String> {
    Mono<Producto> findByNombre(String nombre);
    Flux<Producto> findByIdLocalComercial(String idTienda);
} 
