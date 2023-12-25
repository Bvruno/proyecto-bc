package com.nttdata.bc.compras.clients.dto;

import com.nttdata.bc.compras.models.Producto;
import lombok.Builder;
import lombok.Data;

@Data
public class ProductoDto {

    public static Responce convertToReturn(Request request) {
        return Responce.builder()
                .id(request.getId())
                .nombre(request.getNombre())
                .precio(request.getPrecio())
                .descripcion(request.getDescripcion())
                .idLocalComercial(request.getIdLocalComercial())
                .build();
    }

    public static Request convertToRequest(Responce val) {
        return Request.builder()
                .id(val.getId())
                .nombre(val.getNombre())
                .precio(val.getPrecio())
                .descripcion(val.getDescripcion())
                .idLocalComercial(val.getIdLocalComercial())
                .unidades(val.getUnidades())
                .build();
    }

    public static Responce convertToResponce(Producto val) {
        return Responce.builder()
                .id(val.getId())
                .nombre(val.getNombre())
                .precio(val.getPrecio())
                .descripcion(val.getDescripcion())
                .idLocalComercial(val.getIdLocalComercial())
                .unidades(val.getUnidades())
                .build();
    }

    public static Producto convertToEntity(Request request) {
        return Producto.builder()
                .id(request.getId())
                .nombre(request.getNombre())
                .precio(request.getPrecio())
                .descripcion(request.getDescripcion())
                .idLocalComercial(request.getIdLocalComercial())
                .unidades(request.getUnidades())
                .build();
    }

    @Data
    @Builder
    public static class Request {
        private String id;
        private String nombre;
        private double precio;
        private String descripcion;
        private String idLocalComercial;
        private int unidades;
    }

    @Data
    @Builder
    public static class Responce {
        private String id;
        private String nombre;
        private double precio;
        private String descripcion;
        private String idLocalComercial;
        private int unidades;

    }
}
