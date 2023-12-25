package com.nttdata.bc.compras.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "localesComerciales")
public class LocalComercial {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String horario;

    private String idUsuario;
}
