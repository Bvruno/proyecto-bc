package com.nttdata.bc.usuarios.controllers.dto;

import com.nttdata.bc.usuarios.controllers.dto.response.DniResponse;
import com.nttdata.bc.usuarios.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Data
@Slf4j
public class UsuarioVerificarDniDto {

    public static Response convertToResponse(Usuario usuario) {
        Predicate<String> isApellidoPaternoNull  = Objects::nonNull;
        return isApellidoPaternoNull.test(usuario.getApellidoPaterno()) ? new Response(true) : new Response(false);
    }

    public static Response convertToResponse(DniResponse dniResponse, UsuarioVerificarDniDto.Request request) {
        Predicate<String> isApellidoPaternoNull  = Objects::nonNull;
        BiPredicate<String, String> isMatchingApellidos  = (apellidoPaterno, apellidoMaterno) ->
                apellidoPaterno.equalsIgnoreCase(request.getApellidoPaterno()) &&
                        apellidoMaterno.equalsIgnoreCase(request.getApellidoMaterno());
        return isApellidoPaternoNull.test(dniResponse.getApellidoPaterno()) &&
                isMatchingApellidos.test(dniResponse.getApellidoPaterno(), dniResponse.getApellidoMaterno()) ?
                        new Response(true) : new Response(false);
    }

    public static Usuario convertToEntity(UsuarioVerificarDniDto.Request request) {
        return Usuario.builder()
                .dni(request.getDni())
                .nombres(request.getNombres())
                .apellidoPaterno(request.getApellidoPaterno())
                .apellidoMaterno(request.getApellidoMaterno())
                .build();
    }


    @Data
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
