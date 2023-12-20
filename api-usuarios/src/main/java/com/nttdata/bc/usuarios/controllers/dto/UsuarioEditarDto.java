package com.nttdata.bc.usuarios.controllers.dto;

import com.nttdata.bc.usuarios.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UsuarioEditarDto {

    public static Usuario convertToUsuarioSave(Request request, Usuario usuario) {
        return Usuario.builder()
                .id(usuario.getId())
                .dni(usuario.getDni())
                .nombreCompleto(usuario.getNombreCompleto())
                .nombres(usuario.getNombres())
                .apellidoPaterno(usuario.getApellidoPaterno())
                .apellidoMaterno(usuario.getApellidoMaterno())
                .email(usuario.getEmail())
                .emailValidado(usuario.isEmailValidado())
                .password(usuario.getPassword())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .telefonoValidado(usuario.isTelefonoValidado())
                .rol(request.getRol())
                .activo(request.isActivo())
                .build();
    }

    @Data
    @AllArgsConstructor
    public static class Response {
        private String nombreCompleto;
        private String nombres;
        private boolean isEmailValidado;
        private boolean isTelefonoValidado;
        private boolean isActivo;
    }

    @Data
    @AllArgsConstructor
    public static class Request {
        private String dni; //TODO: no se puede editar

        private String password; //TODO: para editar el password, se debe validar el password actual
        private String direccion;

        private String telefono; //TODO: para editar el telefono, se debe tener un correo validado
        private String codigoValidacionTelefono;

        private String email; //TODO: para editar el email, se debe tener un telefono validado
        private String codigoValidacionEmail;

        private String rol; //TODO: solo el admin puede editar el rol
        private boolean isActivo; //TODO: para editar el estado, se debe tener un correo validado y/o un telefono validado
    }
}
