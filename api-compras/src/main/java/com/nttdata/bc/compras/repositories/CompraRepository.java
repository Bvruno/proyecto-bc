package com.nttdata.bc.compras.repositories;

import com.nttdata.bc.compras.models.Compra;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface CompraRepository extends ReactiveMongoRepository<Compra, String> {
    @Query(value = "{ 'listaProductos.idLocalComercial' : ?0 }")
    Flux<Compra> findByProductoIdLocalComercial(String idLocalComercial);
    Flux<Compra> findByIdUsuario(String idTienda);
} 
