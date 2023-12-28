package com.nttdata.bc.compras.controllers.dto;

import com.nttdata.bc.compras.models.Compra;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CompraDto {

    public static Responce convertToResponce(Compra compra) {
        List<CompraDto.ProductoDto> listaProductos = compra.getListaProductos().stream()
                .map(producto -> CompraDto.ProductoDto.builder()
                        .nombre(producto.getId())
                        .precio(producto.getPrecio())
                        .unidades(producto.getUnidades())
                        .build())
                .collect(Collectors.toList());

        return Responce.builder()
                .idUsuario(compra.getIdUsuario())
                .listaProductos(listaProductos)
                .montoTotal(compra.getMontoTotal())
                .build();
    }

    public static Compra convertToEntity(Request request) {
        List<Compra.Producto> listaProductos = request.getListaProductos().stream()
                .map(producto -> Compra.Producto.builder()
                        .id(producto.getNombre())
                        .precio(producto.getPrecio())
                        .unidades(producto.getUnidades())
                        .build())
                .collect(Collectors.toList());

        return Compra.builder()
                .id(request.getId())
                .idUsuario(request.getIdUsuario())
                .listaProductos(listaProductos)
                .montoTotal(request.getMontoTotal())
                .fechaCompra(System.currentTimeMillis())
                .build();
    }

    @Data
    @Builder
    public static class Request {
        private String id;
        private String idUsuario;
        private List<CompraDto.ProductoDto> listaProductos;
        private double montoTotal;
    }

    @Data
    @Builder
    public static class Responce {
        private String idUsuario;
        private List<CompraDto.ProductoDto> listaProductos;
        private double montoTotal;
    }

    @Data
    @Builder
    public static class ProductoDto {
        private String id;
        private String nombre;
        private double precio;
        private String idLocalComercial;
        private int unidades;
    }
}
