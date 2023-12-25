package com.nttdata.bc.compras.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Document(collection = "compras")
public class Compra {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String idUsuario;
    private List<Producto> listaProductos;
    private double montoTotal;

    @Data
    @Builder
    public static class Producto {
        private String id;
        private double precio;
        private int unidades;
    }
}
