package com.nttdata.bc.compras.clients.dto;

import com.nttdata.bc.compras.models.Producto;
import lombok.Builder;
import lombok.Data;

@Data
public class ProductoDto {

    public static Request convertToRequest(Response val) {
        return Request.builder()
                .id(val.getId())
                .nombre(val.getNombre())
                .precio(val.getPrecio())
                .descripcion(val.getDescripcion())
                .idLocalComercial(val.getIdLocalComercial())
                .unidades(val.getUnidades())
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
    public static class Response {
        private String id;
        private String nombre;
        private double precio;
        private String descripcion;
        private String idLocalComercial;
        private int unidades;

    }
}
