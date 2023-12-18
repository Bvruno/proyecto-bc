package com.nttdata.bc.usuarios.repository;

import com.nttdata.bc.usuarios.models.Codigo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CodigosRepository extends ReactiveMongoRepository<Codigo, Integer> {
    Mono<Codigo> findByCodigo(Integer codigo);
    Mono<Codigo> findByNumeroTelefonico(String numeroTelefonico);
    void deleteByCodigo(Integer codigo);
}
