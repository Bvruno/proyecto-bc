package com.nttdata.bc.usuarios.controllers.dto;

import com.nttdata.bc.usuarios.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Builder
public class UsuarioEditarDto {
    private String id;
    private String dni;
    private String nombreCompleto;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private boolean emailValidado;
    private String password;
    private String direccion;
    private String telefono;
    private boolean telefonoValidado;
    private String rol;
    private double saldo;
    private boolean activo;

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
                .password(request.getPassword())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .telefonoValidado(usuario.isTelefonoValidado())
                .rol(request.getRol())
                .saldo(request.getSaldo())
                .activo(request.isActivo())
                .build();
    }

    public static UsuarioEditarDto convertToDto(Usuario usuario) {
        return UsuarioEditarDto.builder()
                .id(usuario.getId())
                .dni(usuario.getDni())
                .nombreCompleto(usuario.getNombreCompleto())
                .nombres(usuario.getNombres())
                .apellidoPaterno(usuario.getApellidoPaterno())
                .apellidoMaterno(usuario.getApellidoMaterno())
                .email(usuario.getEmail())
                .emailValidado(usuario.isEmailValidado())
                .password(usuario.getPassword())
                .direccion(usuario.getDireccion())
                .telefono(usuario.getTelefono())
                .telefonoValidado(usuario.isTelefonoValidado())
                .rol(usuario.getRol())
                .saldo(usuario.getSaldo())
                .activo(usuario.isActivo())
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

        private double saldo;

        private boolean isActivo; //TODO: para editar el estado, se debe tener un correo validado y/o un telefono validado
    }
}
