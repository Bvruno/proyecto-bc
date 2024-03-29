package com.nttdata.bc.usuarios.controllers.dto;

import com.nttdata.bc.usuarios.controllers.dto.response.DniResponse;
import com.nttdata.bc.usuarios.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Data
@Slf4j
public class ValidarDniDto {

    public static Response isApellidoPaterno(Usuario usuario) {
        Predicate<String> isApellidoPaternoNull  = Objects::nonNull;
        return isApellidoPaternoNull.test(usuario.getApellidoPaterno()) ? new Response(true) : new Response(false);
    }

    public static Response isApellidoPaterno(DniResponse dniResponse, ValidarDniDto.Request request) {
        Predicate<String> isApellidoPaternoNull  = Objects::nonNull;
        BiPredicate<String, String> isMatchingApellidos  = (apellidoPaterno, apellidoMaterno) ->
                apellidoPaterno.equalsIgnoreCase(request.getApellidoPaterno()) &&
                        apellidoMaterno.equalsIgnoreCase(request.getApellidoMaterno());
        return isApellidoPaternoNull.test(dniResponse.getApellidoPaterno()) &&
                isMatchingApellidos.test(dniResponse.getApellidoPaterno(), dniResponse.getApellidoMaterno()) ?
                        new Response(true) : new Response(false);
    }

    public static Boolean validarCredenciales(DniResponse dniResponse, ValidarDniDto.Request request) {
        Predicate<String> isApellidoPaternoNull  = Objects::nonNull;
        BiPredicate<String, String> isMatchingApellidos  = (apellidoPaterno, apellidoMaterno) ->
                apellidoPaterno.equalsIgnoreCase(request.getApellidoPaterno()) &&
                        apellidoMaterno.equalsIgnoreCase(request.getApellidoMaterno());
        return isApellidoPaternoNull.test(dniResponse.getApellidoPaterno()) &&
                isMatchingApellidos.test(dniResponse.getApellidoPaterno(), dniResponse.getApellidoMaterno());
    }

    public static Usuario convertToEntity(ValidarDniDto.Request request) {
        return Usuario.builder()
                .dni(request.getDni())
                .nombres(request.getNombres())
                .apellidoPaterno(request.getApellidoPaterno())
                .apellidoMaterno(request.getApellidoMaterno())
                .build();
    }

    public static Response convertToResponse(Response val) {
        return new Response(val.isCorrecto());
    }


    @Data
    @AllArgsConstructor
    public static class Request {
        private String dni;
        private String nombres;
        private String apellidoPaterno;
        private String apellidoMaterno;
    }

    @Data
    @AllArgsConstructor
    public static class Response {
        private boolean isCorrecto;
    }

}
