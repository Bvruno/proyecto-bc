package com.nttdata.bc.compras.clients.dto;

import com.nttdata.bc.compras.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDto {

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
    private double saldo;
    private String rol;
    private boolean activo;

    public static UsuarioDto convertToDto(Usuario usuario) {
        return UsuarioDto.builder()
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
                .activo(usuario.isActivo())
                .build();
    }

    public static Usuario convertToUsuarioSave(UsuarioDto.Request request, Usuario usuario) {
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

    public static UsuarioDto.Request convertToRequest(UsuarioDto usuario) {
        return Request.builder()
                .id(usuario.getId())
                .dni(usuario.getDni())
                .password(usuario.getPassword())
                .direccion(usuario.getDireccion())
                .telefono(usuario.getTelefono())
                .email(usuario.getEmail())
                .saldo(usuario.getSaldo())
                .rol(usuario.getRol())
                .isActivo(usuario.isActivo())
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
    @Builder
    public static class Request {
        private String id;
        private String dni; //TODO: no se puede editar

        private String password; //TODO: para editar el password, se debe validar el password actual
        private String direccion;

        private String telefono; //TODO: para editar el telefono, se debe tener un correo validado
        private String codigoValidacionTelefono;

        private String email; //TODO: para editar el email, se debe tener un telefono validado
        private String codigoValidacionEmail;

        private double saldo;

        private String rol; //TODO: solo el admin puede editar el rol
        private boolean isActivo; //TODO: para editar el estado, se debe tener un correo validado y/o un telefono validado
    }
}
