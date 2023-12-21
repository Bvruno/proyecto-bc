package com.nttdata.bc.localcomercial.repositories;

import com.nttdata.bc.localcomercial.models.LocalComercial;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface LocalComercialRepository extends ReactiveMongoRepository<LocalComercial, String> {
    Mono<LocalComercial> findByNombre(String nombre);
    Mono<LocalComercial> findByIdTienda(String idTienda);
} 
