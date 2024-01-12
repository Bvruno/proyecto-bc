package com.nttdata.bc.usuarios.controllers;

import com.nttdata.bc.usuarios.controllers.dto.*;
import com.nttdata.bc.usuarios.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping(value = "/listarUsuarios")
    public ResponseEntity<Flux<UsuarioDto>> getAllUsers() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping(value = "/dni/{dni}")
    public ResponseEntity<Mono<UsuarioDto>> getUserByDni(@PathVariable String dni) {
        log.info("getUserByDni Request: {}", dni);
        return ResponseEntity.ok(usuarioService.findByDni(dni));
    }

    @GetMapping(value = "/{id}")
    public Mono<UsuarioDto> getUserById(@PathVariable String id) {
        log.info("getUserById Request: {}", id);
        return usuarioService.findById(id);
    }

    @PostMapping(value = "/validarDNI")
    public ResponseEntity<Mono<ValidarDniDto.Response>> verificarDNI(
            @RequestBody ValidarDniDto.Request request
    ) {
        log.info("verificarDNI Request: {}", request);
        return ResponseEntity.ok(usuarioService.verificarDNI(request));
    }

    @PostMapping(value = "/validarTelefono/crearCodigo")
    public ResponseEntity<Mono<ValidarTelefonoDTO.Response>> validarTelenofo(
            @RequestBody ValidarTelefonoDTO.Request request
    ) {
        log.info("validarTelefono Request: {}", request);
        return ResponseEntity.ok(usuarioService.validarTelefono(request));
    }

    @PostMapping(value = {"/validarTelefono/validarCodigo", "/validarEmail/validarCodigo"})
    public ResponseEntity<Mono<ValidarCodigoDTO.Response>> validarCodigo(
            @RequestBody ValidarCodigoDTO.Request request
    ) {
        log.info("validarCodigo Request: {}", request);
        return ResponseEntity.ok(usuarioService.validarCodigo(request));
    }

    @PostMapping(value = "/validarEmail/crearCodigo")
    public ResponseEntity<Mono<Object>> validarEmail(@PathVariable String email) {
        log.info("validarEmail Request: {}", email);
        return ResponseEntity.ok(usuarioService.validarEmail(email));
    }

    @PostMapping(value = "/crearUsuario")
    public ResponseEntity<Mono<UsuarioCrearDto.Response>> createUsuario(
            @RequestBody UsuarioCrearDto.Request user
    ) {
        log.info("createUsuario Request: {}", user);
        return ResponseEntity.ok(usuarioService.crearUsuario(user));
    }

    @PutMapping("/editar")
    public ResponseEntity<Mono<UsuarioEditarDto>> updateUser(
            @RequestBody UsuarioEditarDto.Request user
    ) {
        log.info("updateUser Request: {}", user);
        return ResponseEntity.ok(usuarioService.save(user));
    }

    @PutMapping("/editarSaldo")
    public ResponseEntity<Mono<UsuarioDto>> updateSaldoUser(
            @RequestBody UsuarioEditarDto.Request user
    ) {
        log.info("updateSaldoUser Request: {}", user);
        return ResponseEntity.ok(usuarioService.actualizarSaldo(user));
    }

    @PatchMapping("/deshabilitar/{dni}")
    public ResponseEntity<Mono<UsuarioDto>> disableUser(@PathVariable String dni) {
        log.info("disableUser Request: {}", dni);
        return ResponseEntity.ok(usuarioService.disableUser(dni));
    }

    @DeleteMapping("/eliminar/{dni}")
    public ResponseEntity<Mono<?>> deleteUser(@PathVariable String dni) {
        log.info("deleteUser Request: {}", dni);
        return ResponseEntity.ok(usuarioService.deleteUser(dni));
    }
}
