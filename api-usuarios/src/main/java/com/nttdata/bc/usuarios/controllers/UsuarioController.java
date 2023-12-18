package com.nttdata.bc.usuarios.controllers;

import com.nttdata.bc.usuarios.controllers.dto.UsuarioCrearDto;
import com.nttdata.bc.usuarios.controllers.dto.UsuarioVerificarDniDto;
import com.nttdata.bc.usuarios.models.Usuario;
import com.nttdata.bc.usuarios.services.UsuarioServices;
import com.twilio.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@Slf4j
public class UsuarioController {

    @Autowired
    private UsuarioServices usuarioServices;

    @GetMapping
    public Flux<Usuario> getAllUsers() {
        return usuarioServices.findAll();
    }

    @PostMapping
    public Mono<ResponseEntity<UsuarioCrearDto.Response>> createUsuario(@RequestBody UsuarioCrearDto.Request user) {
        return usuarioServices.crearUsuario(user)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{dni}")
    public Mono<Usuario> getUserByDni(@PathVariable String dni) {
        return usuarioServices.findByDni(dni);
    }

    @PostMapping(value = "/validarDNI")
    public Mono<UsuarioVerificarDniDto.Response> verificarDNI(@RequestBody UsuarioVerificarDniDto.Request request) {
        return usuarioServices.verificarDNI(request);
    }

    @PostMapping(value = "/validarTelefono/{numeroTelefonico}")
    public ResponseEntity<?> validarTelenofo(@PathVariable String numeroTelefonico) {
        try {
            return usuarioServices.validarTelefono(numeroTelefonico)
                    .map(ResponseEntity::ok)
                    .defaultIfEmpty(ResponseEntity.notFound().build()).block();
        } catch (ApiException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("El numero ingresado debe tener el código del pais");
        }
    }

    @PostMapping(value = "/validarEmail/{email}")
    public ResponseEntity<?> validarEmail(@PathVariable String email) {
        return usuarioServices.validarEmail(email)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build()).block();
    }

    @PutMapping("/{dni}")
    public Mono<ResponseEntity<Usuario>> updateUser(@PathVariable String dni, @RequestBody Usuario user) {
        user.setDni(dni);
        return usuarioServices.save(user)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<?> deleteUser(@PathVariable String dni) {
        return ResponseEntity.ok(usuarioServices.findByDni(dni));
    }

}
