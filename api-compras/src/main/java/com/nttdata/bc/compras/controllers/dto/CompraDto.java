package com.nttdata.bc.compras.controllers.dto;

import com.nttdata.bc.compras.models.Compra;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CompraDto {

    public static Responce convertToReturn(Request request) {
        return Responce.builder()
                .idUsuario(request.getIdUsuario())
                .listaProductos(request.getListaProductos())
                .montoTotal(request.getMontoTotal())
                .build();
    }

    public static Request convertToRequest(Responce responce) {
        return Request.builder()
                .idUsuario(responce.getIdUsuario())
                .listaProductos(responce.getListaProductos())
                .montoTotal(responce.getMontoTotal())
                .build();
    }

    public static Responce convertToResponce(Compra compra) {
        List<CompraDto.ProductoDto> listaProductos = compra.getListaProductos().stream()
                .map(producto -> CompraDto.ProductoDto.builder()
                        .nombre(producto.getNombre())
                        .precio(producto.getPrecio())
                        .idLocalComercial(producto.getIdLocalComercial())
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
                        .nombre(producto.getNombre())
                        .precio(producto.getPrecio())
                        .idLocalComercial(producto.getIdLocalComercial())
                        .unidades(producto.getUnidades())
                        .build())
                .collect(Collectors.toList());

        return Compra.builder()
                .id(request.getId())
                .idUsuario(request.getIdUsuario())
                .listaProductos(listaProductos)
                .montoTotal(request.getMontoTotal())
                .build();
    }

    @Data
    @Builder
    public static class Request {
        private String id;
        private String idUsuario;
        private List<CompraDto.ProductoDto> listaProductos;
        private int montoTotal;
    }

    @Data
    @Builder
    public static class Responce {
        private String idUsuario;
        private List<CompraDto.ProductoDto> listaProductos;
        private int montoTotal;
    }

    @Data
    @Builder
    public static class ProductoDto {
        private String nombre;
        private double precio;
        private String idLocalComercial;
        private int unidades;
    }
}