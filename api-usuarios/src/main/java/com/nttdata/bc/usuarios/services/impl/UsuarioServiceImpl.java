package com.nttdata.bc.usuarios.services.impl;

import com.nttdata.bc.usuarios.controllers.dto.*;
import com.nttdata.bc.usuarios.exceptions.BadRequestException;
import com.nttdata.bc.usuarios.models.Usuario;
import com.nttdata.bc.usuarios.models.Validacion;
import com.nttdata.bc.usuarios.repositories.UsuariosRepository;
import com.nttdata.bc.usuarios.repositories.ValidacionRepository;
import com.nttdata.bc.usuarios.services.ApisNetService;
import com.nttdata.bc.usuarios.services.SMSService;
import com.nttdata.bc.usuarios.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private ValidacionRepository validacionRepository;

    @Autowired
    private ApisNetService apisNetService;

    @Autowired
    private SMSService smsService;

    @Override
    public Mono<UsuarioCrearDto.Response> crearUsuario(
            UsuarioCrearDto.Request request
    ) {
        return usuariosRepository
                .findByDni(request.getDni())
                .switchIfEmpty(Mono.error(new BadRequestException("El DNI no existe")))
                .flatMap(usuario ->
                        usuario.isActivo()
                                ? Mono.just(
                                UsuarioCrearDto.Response
                                        .builder()
                                        .isActivo(true)
                                        .mensaje("El usuario ya existe")
                                        .build()
                        )
                                : usuario.isTelefonoValidado()
                                ? usuariosRepository
                                .save(UsuarioCrearDto.convertToUsuarioSave(request, usuario))
                                .switchIfEmpty(
                                        Mono.error(new BadRequestException("Error al crear el usuario"))
                                )
                                .flatMap(usuarioValidado ->
                                        Mono.just(
                                                UsuarioCrearDto.Response
                                                        .builder()
                                                        .isActivo(true)
                                                        .mensaje("Usuario creado correctamente")
                                                        .build()
                                        )
                                )
                                : Mono.just(
                                UsuarioCrearDto.Response
                                        .builder()
                                        .isActivo(false)
                                        .mensaje(
                                                "No se puede crear el usuario, el telefono no esta validado"
                                        )
                                        .build()
                        )
                );
    }

    @Override
    public Mono<ValidarDniDto.Response> verificarDNI(
            ValidarDniDto.Request request
    ) {
        return usuariosRepository
                .findByDni(request.getDni())
                .switchIfEmpty(
                        apisNetService
                                .consultarDNI(request.getDni())
                                .switchIfEmpty(
                                        Mono.error(new BadRequestException("Error al consultar el dni"))
                                )
                                .flatMap(dniResponse ->
                                        ValidarDniDto.validarCredenciales(dniResponse, request)
                                                ? usuariosRepository.save(ValidarDniDto.convertToEntity(request))
                                                : Mono.empty()
                                )
                                .switchIfEmpty(
                                        Mono.error(new BadRequestException("Error al guardar el usuario"))
                                )
                )
                .map(ValidarDniDto::isApellidoPaterno);
    }

    @Override
    public Flux<UsuarioDto> findAll() {
        return usuariosRepository.findAll()
                .switchIfEmpty(Mono.error(new BadRequestException("Error al encontrar los usuarios")))
                .flatMap(usuario -> Mono.just(UsuarioDto.convertToDto(usuario)));
    }

    @Override
    public Mono<UsuarioDto> findByDni(String dni) {
        return usuariosRepository
                .findByDni(dni)
                .switchIfEmpty(
                        Mono.error(new BadRequestException("Error al encontrar el usuario"))
                )
                .flatMap(usuario -> Mono.just(UsuarioDto.convertToDto(usuario)));
    }

    @Override
    public Mono<UsuarioEditarDto> save(UsuarioEditarDto.Request request) {
        log.info("Request: {}", request);
        return usuariosRepository.findByDni(request.getDni())
                .switchIfEmpty(Mono.error(new BadRequestException("Error al encontrar el usuario")))
                .flatMap(usuario ->
                        usuariosRepository.save(UsuarioEditarDto.convertToUsuarioSave(request, usuario))
                                .switchIfEmpty(Mono.error(new BadRequestException("Error al guardar el usuario")))
                                .flatMap(usuarioValidado -> Mono.just(UsuarioEditarDto.convertToDto(usuarioValidado))));
    }

    @Override
    public void deleteByDni(String ruc) {
        usuariosRepository.deleteByDni(ruc);
    }

    @Override
    public Mono<ValidarTelefonoDTO.Response> validarTelefono(ValidarTelefonoDTO.Request request) {
        Mono<Validacion> validacionMono = validacionRepository.save(
                ValidarTelefonoDTO.convertToEntity(request, generarCodigo()));

        return validacionRepository
                .findByTelefono(request.getTelefono())
                .switchIfEmpty(validacionMono)
                .flatMap(validacion ->
                        ValidarTelefonoDTO.validarTiempoCreacion(validacion)
                                ? Mono.just(validacion)
                                : validacionRepository.delete(validacion).then(validacionMono)
                )
                .flatMap(validacion ->
                        smsService
                                .enviarSMS(
                                        request.getTelefono(),
                                        String.valueOf(validacion.getCodigo())
                                )
                                .flatMap(message ->
                                        Mono.just(
                                                ValidarTelefonoDTO.Response
                                                        .builder()
                                                        .tiempoCreacion(validacion.getTiempoCreacion())
                                                        .build()
                                        )
                                )
                );
    }

    private int generarCodigo() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        //return random.nextInt(max - min + 1) + min;
        return 789456;
    }

    @Override
    public Mono<Object> validarEmail(String email) {
        return Mono.just(email);
    }

    @Override
    public Mono<ValidarCodigoDTO.Response> validarCodigo(
            ValidarCodigoDTO.Request request
    ) {
        return validacionRepository
                .findByCodigo(request.getCodigo())
                .switchIfEmpty(
                        Mono.error(new BadRequestException("No se encontro el codigo"))
                )
                .flatMap(validacion ->
                        usuariosRepository
                                .findByDni(validacion.getDni())
                                .switchIfEmpty(
                                        Mono.error(new BadRequestException("Error al encontrar el usuario"))
                                )
                                .flatMap(usuario ->
                                        ValidarCodigoDTO.telefonoValidado(usuario)
                                                ? validacionRepository
                                                .delete(validacion)
                                                .then(
                                                        Mono.just(
                                                                ValidarCodigoDTO.Response
                                                                        .builder()
                                                                        .validacion(true)
                                                                        .mensaje("El telefono ya fue validado")
                                                                        .build()
                                                        )
                                                )
                                                : usuariosRepository
                                                .save(
                                                        ValidarCodigoDTO.convertToUsuarioValidado(usuario, request)
                                                )
                                                .flatMap(usuarioValidado ->
                                                        validacionRepository
                                                                .delete(validacion)
                                                                .then(
                                                                        Mono.just(
                                                                                ValidarCodigoDTO.Response
                                                                                        .builder()
                                                                                        .validacion(true)
                                                                                        .mensaje("El telefono validado correctamente")
                                                                                        .build()
                                                                        )
                                                                )
                                                )
                                )
                );
    }

    @Override
    public Mono<?> deleteUser(String dni) {
        return usuariosRepository
                .findByDni(dni)
                .switchIfEmpty(
                        Mono.error(new BadRequestException("Error al encontrar el usuario"))
                )
                .flatMap(usuario ->
                        usuariosRepository
                                .delete(usuario)
                                .then(Mono.empty())
                );
    }

    @Override
    public Mono<UsuarioDto> disableUser(String dni) {
        return usuariosRepository.findByDni(dni)
                .switchIfEmpty(Mono.error(new BadRequestException("Error al encontrar el usuario")))
                .flatMap(usuario ->
                        usuariosRepository.save(Usuario.builder().dni(dni).activo(false).build())
                                .flatMap(usuarioValidado ->
                                        Mono.just(UsuarioDto.convertToDto(usuarioValidado)))
                );
    }

    @Override
    public Mono<UsuarioDto> findById(String id) {
        return usuariosRepository.findById(id)
                .switchIfEmpty(Mono.error(new BadRequestException("Error al encontrar el usuario")))
                .flatMap(usuario -> Mono.just(UsuarioDto.convertToDto(usuario)));
    }

    @Override
    public Mono<UsuarioDto> actualizarSaldo(UsuarioEditarDto.Request user) {
        return usuariosRepository.findByDni(user.getDni())
                .switchIfEmpty(Mono.error(new BadRequestException("Error al encontrar el usuario")))
                .flatMap(usuario -> {
                    usuario.setSaldo(user.getSaldo());
                    return usuariosRepository.save(usuario)
                            .switchIfEmpty(Mono.error(new BadRequestException("Error al actualizar el saldo")))
                            .flatMap(usuarioValidado -> Mono.just(UsuarioDto.convertToDto(usuarioValidado)));
                });
    }
}
