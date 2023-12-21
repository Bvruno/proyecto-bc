package com.nttdata.bc.productos.controllers.dto;

import com.nttdata.bc.productos.models.Producto;
import lombok.Builder;
import lombok.Data;
import org.reactivestreams.Publisher;

@Data
public class ProductoDto {

    public static ProductoDto.Responce convertToReturn(ProductoDto.Request request) {
        return ProductoDto.Responce.builder()
                .id(request.getId())
                .nombre(request.getNombre())
                .precio(request.getPrecio())
                .descripcion(request.getDescripcion())
                .idTienda(request.getIdTienda())
                .build();
    }

    public static ProductoDto.Request convertToRequest(ProductoDto.Responce val) {
        return ProductoDto.Request.builder()
                .id(val.getId())
                .nombre(val.getNombre())
                .precio(val.getPrecio())
                .descripcion(val.getDescripcion())
                .idTienda(val.getIdTienda())
                .build();
    }

    public static ProductoDto.Responce convertToResponce(Producto val) {
        return ProductoDto.Responce.builder()
                .id(val.getId())
                .nombre(val.getNombre())
                .precio(val.getPrecio())
                .descripcion(val.getDescripcion())
                .idTienda(val.getIdTienda())
                .build();
    }

    public static Producto convertToEntity(ProductoDto.Request request) {
        return Producto.builder()
                .id(request.getId())
                .nombre(request.getNombre())
                .precio(request.getPrecio())
                .descripcion(request.getDescripcion())
                .idTienda(request.getIdTienda())
                .build();
    }

    @Data
    @Builder
    public static class Request {
        private String id;
        private String nombre;
        private double precio;
        private String descripcion;
        private String idTienda;
    }

    @Data
    @Builder
    public static class Responce {
        private String id;
        private String nombre;
        private double precio;
        private String descripcion;
        private String idTienda;

    }
}
