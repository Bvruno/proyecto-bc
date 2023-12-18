package com.nttdata.bc.usuarios.services;

import com.nttdata.bc.usuarios.controllers.dto.UsuarioCrearDto;
import com.nttdata.bc.usuarios.controllers.dto.UsuarioVerificarDniDto;
import com.nttdata.bc.usuarios.models.Codigo;
import com.nttdata.bc.usuarios.models.Usuario;
import com.nttdata.bc.usuarios.repository.CodigosRepository;
import com.nttdata.bc.usuarios.repository.UsuariosRepository;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
public class UsuarioServices {

    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private CodigosRepository codigosRepository;
    @Autowired
    private ApisNetService apisNetService;
    @Autowired
    private SMSService smsService;

    public Mono<UsuarioCrearDto.Response> crearUsuario(UsuarioCrearDto.Request usuarioDTO) {
        return usuariosRepository.save(UsuarioCrearDto.convertToEntity(usuarioDTO))
                .map(UsuarioCrearDto::convertToDto);
    }

    public Mono<UsuarioVerificarDniDto.Response> verificarDNI(UsuarioVerificarDniDto.Request request) {
        return usuariosRepository.findByDni(request.getDni())
                .map(UsuarioVerificarDniDto::convertToResponse)
                .switchIfEmpty(Objects.requireNonNull(apisNetService.consultarDNI(request.getDni())
                        .map(dniResponse -> UsuarioVerificarDniDto.convertToResponse(dniResponse, request))
                        .map(resp -> usuariosRepository.save(UsuarioVerificarDniDto.convertToEntity(request))
                                .map(UsuarioVerificarDniDto::convertToResponse))
                        .switchIfEmpty(Mono.empty()).block()));
    }

    public Flux<Usuario> findAll() {
        return usuariosRepository.findAll();
    }

    public Mono<Usuario> findByDni(String dni) {
        return usuariosRepository.findByDni(dni);
    }

    public Mono<Usuario> save(Usuario user) {
        return usuariosRepository.save(user);
    }

    public void deleteByDni(String ruc) {
        usuariosRepository.deleteByDni(ruc);
    }

    public Mono<Codigo> validarTelefono(String numeroTelefonico) {
        Random random = new Random();
        int min = 1000;
        int max = 9999;

        Mono<Codigo> codigoMono = codigosRepository.findByNumeroTelefonico(numeroTelefonico);
        codigoMono.map(codigo -> codigo.getTiempoCreacion() + (2 * 60 * 1000) < System.currentTimeMillis() ? null : codigoMono);
        int randomNumber = random.nextInt(max - min + 1) + min;
        Message message = smsService.enviarSMS(numeroTelefonico, String.valueOf(randomNumber));
        log.info("message getStatus: {}", message.getStatus());
        return codigoMono.switchIfEmpty(codigosRepository.save(Codigo.builder()
                .codigo(randomNumber)
                .tiempoCreacion(System.currentTimeMillis())
                .numeroTelefonico(numeroTelefonico)
                .build()));
    }

    public Mono<Object> validarEmail(String email) {
        return Mono.just(email);
    }
}
