package com.nttdata.bc.usuarios.repository;

import com.nttdata.bc.usuarios.models.Validacion;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

public interface ValidacionRepository extends ReactiveMongoRepository<Validacion, String> {
    Mono<Validacion> findByCodigo(Integer codigo);
    Mono<Validacion> findByTelefono(String numeroTelefonico);
    void deleteByCodigo(Integer codigo);

    void deleteByDni(String dni);
}
