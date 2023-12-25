package com.nttdata.bc.usuarios.controllers.dto;

import com.nttdata.bc.usuarios.models.Usuario;
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
    private String rol;
    private double saldo;
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
                .saldo(usuario.getSaldo())
                .build();
    }
}
