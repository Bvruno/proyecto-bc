package com.nttdata.bc.localcomercial.controllers.dto;

import com.nttdata.bc.localcomercial.models.LocalComercial;
import lombok.Builder;
import lombok.Data;

@Data
public class LocalComercialDto {

    public static Response convertToResponse(Request request) {
        return Response.builder()
                .id(request.getId())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .direccion(request.getDireccion())
                .horario(request.getHorario())
                .idUsuario(request.getIdUsuario())
                .build();
    }

    public static Request convertToRequest(Response response) {
        return Request.builder()
                .id(response.getId())
                .nombre(response.getNombre())
                .descripcion(response.getDescripcion())
                .direccion(response.getDireccion())
                .horario(response.getHorario())
                .idUsuario(response.getIdUsuario())
                .build();
    }

    public static Response convertToResponse(LocalComercial localComercial) {
        return Response.builder()
                .id(localComercial.getId())
                .nombre(localComercial.getNombre())
                .descripcion(localComercial.getDescripcion())
                .direccion(localComercial.getDireccion())
                .horario(localComercial.getHorario())
                .idUsuario(localComercial.getIdUsuario())
                .build();
    }

    public static LocalComercial convertToEntity(Request request) {
        return LocalComercial.builder()
                .id(request.getId())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .direccion(request.getDireccion())
                .horario(request.getHorario())
                .idUsuario(request.getIdUsuario())
                .build();
    }

    @Data
    @Builder
    public static class Request {
        private String id;
        private String nombre;
        private String descripcion;
        private String direccion;
        private String horario;

        private String idUsuario;
    }

    @Data
    @Builder
    public static class Response {
        private String id;
        private String nombre;
        private String descripcion;
        private String direccion;
        private String horario;

        private String idUsuario;
    }
}
