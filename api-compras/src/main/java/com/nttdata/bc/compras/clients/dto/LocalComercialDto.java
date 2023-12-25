package com.nttdata.bc.compras.clients.dto;

import com.nttdata.bc.compras.models.LocalComercial;
import lombok.Builder;
import lombok.Data;

@Data
public class LocalComercialDto {

    public static Responce convertToReturn(Request request) {
        return Responce.builder()
                .id(request.getId())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .direccion(request.getDireccion())
                .horario(request.getHorario())
                .idUsuario(request.getIdUsuario())
                .build();
    }

    public static Request convertToRequest(Responce responce) {
        return Request.builder()
                .id(responce.getId())
                .nombre(responce.getNombre())
                .descripcion(responce.getDescripcion())
                .direccion(responce.getDireccion())
                .horario(responce.getHorario())
                .idUsuario(responce.getIdUsuario())
                .build();
    }

    public static Responce convertToResponce(LocalComercial localComercial) {
        return Responce.builder()
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
    public static class Responce {
        private String id;
        private String nombre;
        private String descripcion;
        private String direccion;
        private String horario;

        private String idUsuario;
    }
}
