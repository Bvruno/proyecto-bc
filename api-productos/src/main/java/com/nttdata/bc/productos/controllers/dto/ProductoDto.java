package com.nttdata.bc.productos.controllers.dto;

import com.nttdata.bc.productos.models.Producto;
import lombok.Builder;
import lombok.Data;

@Data
public class ProductoDto {

    public static Response convertToReturn(ProductoDto.Request request) {
        return Response.builder()
                .id(request.getId())
                .nombre(request.getNombre())
                .precio(request.getPrecio())
                .descripcion(request.getDescripcion())
                .unidades(request.getUnidades())
                .idTienda(request.getIdTienda())
                .build();
    }

    public static ProductoDto.Request convertToRequest(Response val) {
        return ProductoDto.Request.builder()
                .id(val.getId())
                .nombre(val.getNombre())
                .precio(val.getPrecio())
                .descripcion(val.getDescripcion())
                .unidades(val.getUnidades())
                .idTienda(val.getIdTienda())
                .build();
    }

    public static Response convertToResponce(Producto val) {
        return Response.builder()
                .id(val.getId())
                .nombre(val.getNombre())
                .precio(val.getPrecio())
                .descripcion(val.getDescripcion())
                .unidades(val.getUnidades())
                .idTienda(val.getIdLocalComercial())
                .build();
    }

    public static Producto convertToEntity(ProductoDto.Request request) {
        return Producto.builder()
                .id(request.getId())
                .nombre(request.getNombre())
                .precio(request.getPrecio())
                .descripcion(request.getDescripcion())
                .unidades(request.getUnidades())
                .idLocalComercial(request.getIdTienda())
                .build();
    }

    @Data
    @Builder
    public static class Request {
        private String id;
        private String nombre;
        private double precio;
        private String descripcion;
        private int unidades;
        private String idTienda;
    }

    @Data
    @Builder
    public static class Response {
        private String id;
        private String nombre;
        private double precio;
        private String descripcion;
        private int unidades;
        private String idTienda;

    }
}
