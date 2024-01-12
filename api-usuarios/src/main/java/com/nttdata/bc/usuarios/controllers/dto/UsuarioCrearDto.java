package com.nttdata.bc.usuarios.controllers.dto;

import com.nttdata.bc.usuarios.models.Usuario;
import com.nttdata.bc.usuarios.models.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class UsuarioCrearDto {

    public static String encrypPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        password = encoder.encode(password);
        return password;
    }

    public static Usuario convertToEntity(Request usuarioCrearDTO) {
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


    public static Usuario convertToUsuarioSave(Request usuarioDTO, Usuario usuario) {
        return Usuario.builder()
                .id(usuario.getId())
                .dni(usuario.getDni())
                .nombreCompleto(usuario.getNombreCompleto())
                .nombres(usuario.getNombres())
                .apellidoPaterno(usuario.getApellidoPaterno())
                .apellidoMaterno(usuario.getApellidoMaterno())
                .email(usuario.getEmail())
                .emailValidado(usuario.isEmailValidado())

                //call encrypPassword function
                .password(encrypPassword(usuarioDTO.getPassword()))

                .direccion(usuarioDTO.getDireccion())
                .telefono(usuario.getTelefono())
                .telefonoValidado(usuario.isTelefonoValidado())
                .rol(Rol.USUARIO.name())
                .activo(true)
                .build();
    }

    @Data
    @Builder
    public static class Response {
        private boolean isActivo;
        private String mensaje;
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
