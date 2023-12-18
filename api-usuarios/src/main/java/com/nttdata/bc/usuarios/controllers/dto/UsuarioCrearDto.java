package com.nttdata.bc.usuarios.controllers.dto;

import com.nttdata.bc.usuarios.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UsuarioCrearDto {

    public static Usuario convertToEntity(UsuarioCrearDto.Request usuarioCrearDTO) {
        return Usuario.builder()
                .dni(usuarioCrearDTO.getDni())
                .nombreCompleto(usuarioCrearDTO.getNombreCompleto())
                .nombres(usuarioCrearDTO.getNombres())
                .apellidoPaterno(usuarioCrearDTO.getApellidoPaterno())
                .apellidoMaterno(usuarioCrearDTO.getApellidoMaterno())
                .email(usuarioCrearDTO.getEmail())
                .emailValidado(usuarioCrearDTO.isEmailValidado())
                .password(usuarioCrearDTO.getPassword())
                .direccion(usuarioCrearDTO.getDireccion())
                .telefono(usuarioCrearDTO.getTelefono())
                .telefonoValidado(usuarioCrearDTO.isTelefonoValidado())
                .rol(usuarioCrearDTO.getRol())
                .activo(usuarioCrearDTO.isActivo())
                .build();
    }

    public static UsuarioCrearDto.Response convertToDto(Usuario usuario) {
        return new UsuarioCrearDto.Response(
                usuario.getNombreCompleto(),
                usuario.getNombres(),
                usuario.isEmailValidado(),
                usuario.isTelefonoValidado(),
                usuario.isActivo()
        );
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
        private String dni;
        private String nombreCompleto;
        private String nombres;
        private String apellidoPaterno;
        private String apellidoMaterno;
        private String email;
        private boolean isEmailValidado;
        private String password;
        private String direccion;
        private String telefono;
        private boolean isTelefonoValidado;
        private String rol;
        private boolean isActivo;
    }

}
