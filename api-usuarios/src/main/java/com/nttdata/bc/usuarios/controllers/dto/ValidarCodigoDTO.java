package com.nttdata.bc.usuarios.controllers.dto;

import com.nttdata.bc.usuarios.models.Usuario;
import com.nttdata.bc.usuarios.models.Validacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Data
public class ValidarCodigoDTO {

    public static boolean validarTiempoCreacionAndCodigoAndDni(Validacion validacion, ValidarCodigoDTO.Request request) {
        Predicate<Validacion> isTiempoCreacionValido = val -> val.getTiempoCreacion() + (2 * 60 * 1000) > System.currentTimeMillis();
        BiPredicate<Validacion, ValidarCodigoDTO.Request > isCodigoAndDniValido = (val , req) -> val.getCodigo().equals(req.getCodigo()) && val.getDni().equals(req.getDni());
        return isTiempoCreacionValido.test(validacion) && isCodigoAndDniValido.test(validacion, request);
    }

    public static boolean telefonoValidado(Usuario usuario) {
        Predicate<Usuario> isValido = Usuario::isTelefonoValidado;
        return isValido.test(usuario);
    }

    public static Usuario convertToUsuarioValidado(Usuario usuario, Request request) {
        return Usuario.builder()
                .id(usuario.getId())
                .dni(usuario.getDni())
                .nombreCompleto(usuario.getNombreCompleto())
                .nombres(usuario.getNombres())
                .apellidoPaterno(usuario.getApellidoPaterno())
                .apellidoMaterno(usuario.getApellidoMaterno())
                .email(request.getEmail())
                .emailValidado(usuario.isEmailValidado())
                .password(usuario.getPassword())
                .direccion(usuario.getDireccion())
                .telefono(request.getTelefono())
                .telefonoValidado(true)
                .rol(usuario.getRol())
                .activo(usuario.isActivo())
                .build();
    }

    public static Response convertToResponse(Boolean isValido) {
        return Response.builder()
                .validacion(true)
                .mensaje("El telefono fue validado correctamente")
                .build();
    }

    @Data
    @AllArgsConstructor
    public static class Request {
        private Integer codigo;
        private String dni;
        private String telefono;
        private String email;
    }

    @Data
    @Builder
    public static class Response {
        private Boolean validacion;
        private String mensaje;
    }
}
