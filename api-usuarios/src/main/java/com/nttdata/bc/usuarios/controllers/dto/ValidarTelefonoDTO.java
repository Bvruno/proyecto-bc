package com.nttdata.bc.usuarios.controllers.dto;

import com.nttdata.bc.usuarios.models.Validacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

@Data
@Slf4j
public class ValidarTelefonoDTO {

    public static Validacion convertToEntity(ValidarTelefonoDTO.Request request, Integer codigo) {
        return Validacion.builder()
                .tiempoCreacion(System.currentTimeMillis())
                .codigo(codigo)
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .dni(request.getDni())
                .build();
    }

    public static boolean validarTiempoCreacion(Validacion validacion) {
        Predicate<Validacion> isValido = val -> val.getTiempoCreacion() + (2 * 60 * 1000) > System.currentTimeMillis();
        return isValido.test(validacion);
    }

    @Data
    @AllArgsConstructor
    public static class Request {
        private int codigo;
        private String email;
        private String telefono;
        private String dni;
    }

    @Data
    @Builder
    public static class Response {
        private long tiempoCreacion;
    }
}
