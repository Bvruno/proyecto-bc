package com.nttdata.bc.usuarios.services;

import com.nttdata.bc.usuarios.controllers.dto.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsuarioService {
    Mono<UsuarioCrearDto.Response> crearUsuario(UsuarioCrearDto.Request request);

    Mono<ValidarDniDto.Response> verificarDNI(ValidarDniDto.Request request);

    Flux<UsuarioDto> findAll();

    Mono<UsuarioDto> findByDni(String dni);

    Mono<UsuarioEditarDto> save(UsuarioEditarDto.Request request);

    void deleteByDni(String dni);

    Mono<ValidarTelefonoDTO.Response> validarTelefono(ValidarTelefonoDTO.Request request);

    Mono<Object> validarEmail(String email);

    Mono<ValidarCodigoDTO.Response> validarCodigo(ValidarCodigoDTO.Request request);

    Mono<?> deleteUser(String dni);

    Mono<UsuarioDto> disableUser(String dni);

    Mono<UsuarioDto> findById(String id);

    Mono<UsuarioDto> actualizarSaldo(UsuarioEditarDto.Request user);
}