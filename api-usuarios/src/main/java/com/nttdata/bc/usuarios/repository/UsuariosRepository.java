package com.nttdata.bc.usuarios.repository;

import com.nttdata.bc.usuarios.models.Usuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UsuariosRepository extends ReactiveMongoRepository<Usuario, String> {
    Mono<Usuario> findByDni(String dni);
    void deleteByDni(String dni);
}
