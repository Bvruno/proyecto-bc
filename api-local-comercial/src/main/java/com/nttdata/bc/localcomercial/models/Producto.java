package com.nttdata.bc.localcomercial.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "productos")
public class Producto {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String nombre;
    private double precio;
    private String descripcion;
    private String idLocalComercial;
    private int unidades;
}
