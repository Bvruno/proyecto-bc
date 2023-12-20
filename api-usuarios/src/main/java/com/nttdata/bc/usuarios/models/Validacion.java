package com.nttdata.bc.usuarios.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "validaciones")
@Builder
public class Validacion {
    @Id
    private String id;
    private Integer codigo;
    private long tiempoCreacion;
    private String telefono;
    private String email;
    private String dni;
}
