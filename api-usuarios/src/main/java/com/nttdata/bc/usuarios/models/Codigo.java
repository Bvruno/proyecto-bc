package com.nttdata.bc.usuarios.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "codigos")
@Builder
public class Codigo {
    @Id
    private Integer codigo;
    private long tiempoCreacion;
    private String numeroTelefonico;
}
