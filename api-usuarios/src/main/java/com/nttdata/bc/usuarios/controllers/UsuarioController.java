package com.nttdata.bc.usuarios.controllers;

import com.nttdata.bc.usuarios.controllers.dto.*;
import com.nttdata.bc.usuarios.models.Usuario;
import com.nttdata.bc.usuarios.services.UsuarioServices;
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

  @GetMapping(value = "/listarUsuarios")
  public ResponseEntity<Flux<Usuario>> getAllUsers() {
    return ResponseEntity.ok(usuarioServices.findAll());
  }

  @GetMapping(value = "/{dni}")
  public ResponseEntity<Mono<Usuario>> getUserByDni(@PathVariable String dni) {
    return ResponseEntity.ok(usuarioServices.findByDni(dni));
  }

  @PostMapping(value = "/validarDNI")
  public ResponseEntity<Mono<ValidarDniDto.Response>> verificarDNI(
    @RequestBody ValidarDniDto.Request request
  ) {
    return ResponseEntity.ok(usuarioServices.verificarDNI(request));
  }

  @PostMapping(value = "/validarTelefono/crearCodigo")
  public ResponseEntity<Mono<ValidarTelefonoDTO.Response>> validarTelenofo(
    @RequestBody ValidarTelefonoDTO.Request request
  ) {
    return ResponseEntity.ok(usuarioServices.validarTelefono(request));
  }

  @PostMapping(
    value = { "/validarTelefono/validarCodigo", "/validarEmail/validarCodigo" }
  )
  public ResponseEntity<Mono<ValidarCodigoDTO.Response>> validarCodigo(
    @RequestBody ValidarCodigoDTO.Request request
  ) {
    return ResponseEntity.ok(usuarioServices.validarCodigo(request));
  }

  @PostMapping(value = "/validarEmail/crearCodigo")
  public ResponseEntity<Mono<Object>> validarEmail(@PathVariable String email) {
    return ResponseEntity.ok(usuarioServices.validarEmail(email));
  }

  @PostMapping(value = "/crearUsuario")
  public ResponseEntity<Mono<UsuarioCrearDto.Response>> createUsuario(
    @RequestBody UsuarioCrearDto.Request user
  ) {
    return ResponseEntity.ok(usuarioServices.crearUsuario(user));
  }

  @PutMapping("/editar")
  public ResponseEntity<Mono<Usuario>> updateUser(
    @RequestBody UsuarioEditarDto.Request user
  ) {
    return ResponseEntity.ok(usuarioServices.save(user));
  }

  @PatchMapping("/deshabilitar/{dni}")
  public ResponseEntity<Mono<Usuario>> disableUser(@PathVariable String dni) {
    return ResponseEntity.ok(usuarioServices.disableUser(dni));
  }

  @DeleteMapping("/eliminar/{dni}")
  public ResponseEntity<Mono<Usuario>> deleteUser(@PathVariable String dni) {
    return ResponseEntity.ok(usuarioServices.deleteUser(dni));
  }
}
