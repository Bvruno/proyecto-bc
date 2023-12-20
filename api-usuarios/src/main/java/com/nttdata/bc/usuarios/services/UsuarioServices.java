package com.nttdata.bc.usuarios.services;

import com.nttdata.bc.usuarios.controllers.dto.*;
import com.nttdata.bc.usuarios.exceptions.BadRequestException;
import com.nttdata.bc.usuarios.models.Validacion;
import com.nttdata.bc.usuarios.models.Usuario;
import com.nttdata.bc.usuarios.repository.ValidacionRepository;
import com.nttdata.bc.usuarios.repository.UsuariosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
@Slf4j
public class UsuarioServices {

    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private ValidacionRepository validacionRepository;
    @Autowired
    private ApisNetService apisNetService;
    @Autowired
    private SMSService smsService;

    public Mono<UsuarioCrearDto.Response> crearUsuario(UsuarioCrearDto.Request request) {
        return usuariosRepository.findByDni(request.getDni())
                .switchIfEmpty(Mono.error(new BadRequestException("El DNI no existe")))
                .flatMap(usuario -> usuario.isActivo() ?
                        Mono.just(UsuarioCrearDto.Response.builder()
                                .isActivo(true)
                                .mensaje("El usuario ya existe")
                                .build()) :
                        usuario.isTelefonoValidado() ?
                                usuariosRepository.save(UsuarioCrearDto.convertToUsuarioSave(request, usuario))
                                        .switchIfEmpty(Mono.error(new BadRequestException("Error al crear el usuario")))
                                        .flatMap(usuarioValidado -> Mono.just(UsuarioCrearDto.Response.builder()
                                                .isActivo(true)
                                                .mensaje("Usuario creado correctamente")
                                                .build())) :
                                Mono.just(UsuarioCrearDto.Response.builder()
                                        .isActivo(false)
                                        .mensaje("No se puede crear el usuario, el telefono no esta validado")
                                        .build())
                );
    }

    public Mono<ValidarDniDto.Response> verificarDNI(ValidarDniDto.Request request) {
        return usuariosRepository.findByDni(request.getDni())
                .switchIfEmpty(
                        apisNetService.consultarDNI(request.getDni())
                                .switchIfEmpty(Mono.error(new BadRequestException("Error al consultar el dni")))
                                .flatMap(dniResponse -> ValidarDniDto.validarCredenciales(dniResponse, request) ?
                                        usuariosRepository.save(ValidarDniDto.convertToEntity(request)) : Mono.empty()
                                )
                                .switchIfEmpty(Mono.error(new BadRequestException("Error al guardar el usuario")))
                )
                .map(ValidarDniDto::isApellidoPaterno);
    }

    public Flux<Usuario> findAll() {
        return usuariosRepository.findAll();
    }

    public Mono<Usuario> findByDni(String dni) {
        return usuariosRepository.findByDni(dni);
    }

    public Mono<Usuario> save(UsuarioEditarDto.Request request) {
        return usuariosRepository.findByDni(request.getDni())
                .switchIfEmpty(Mono.error(new BadRequestException("Error al encontrar el usuario")))
                .flatMap(usuario -> usuariosRepository.save(UsuarioEditarDto.convertToUsuarioSave(request, usuario)))
                .switchIfEmpty(Mono.error(new BadRequestException("Error al guardar el usuario")));
    }

    public void deleteByDni(String ruc) {
        usuariosRepository.deleteByDni(ruc);
    }

    public Mono<ValidarTelefonoDTO.Response> validarTelefono(ValidarTelefonoDTO.Request request) {
        Mono<Validacion> validacionMono = validacionRepository.save(ValidarTelefonoDTO.convertToEntity(request, generarCodigo()));

        return validacionRepository.findByTelefono(request.getTelefono())
                .switchIfEmpty(validacionMono)
                .flatMap(validacion -> ValidarTelefonoDTO.validarTiempoCreacion(validacion) ? Mono.just(validacion) : validacionRepository.delete(validacion)
                        .then(validacionMono)
                )
                .flatMap(validacion -> smsService.enviarSMS(request.getTelefono(), String.valueOf(validacion.getCodigo()))
                        .flatMap(message -> Mono.just(ValidarTelefonoDTO.Response.builder()
                                .tiempoCreacion(validacion.getTiempoCreacion())
                                .build()))
                );
    }

    private int generarCodigo() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        return random.nextInt(max - min + 1) + min;
    }

    public Mono<Object> validarEmail(String email) {
        return Mono.just(email);
    }

    public Mono<ValidarCodigoDTO.Response> validarCodigo(ValidarCodigoDTO.Request request) {
        return validacionRepository.findByCodigo(request.getCodigo())
                .switchIfEmpty(Mono.error(new BadRequestException("No se encontro el codigo")))
                .flatMap(validacion -> usuariosRepository.findByDni(validacion.getDni())
                        .switchIfEmpty(Mono.error(new BadRequestException("Error al encontrar el usuario")))
                        .flatMap(usuario -> ValidarCodigoDTO.telefonoValidado(usuario) ?
                                validacionRepository.delete(validacion)
                                        .then(Mono
                                                .just(ValidarCodigoDTO.Response.builder()
                                                        .validacion(true)
                                                        .mensaje("El telefono ya fue validado")
                                                        .build())) :
                                usuariosRepository.save(ValidarCodigoDTO.convertToUsuarioValidado(usuario, request))
                                        .flatMap(usuarioValidado -> validacionRepository.delete(validacion).then(Mono
                                                .just(ValidarCodigoDTO.Response.builder()
                                                        .validacion(true)
                                                        .mensaje("El telefono validado correctamente")
                                                        .build())))
                        )
                );
    }
}
