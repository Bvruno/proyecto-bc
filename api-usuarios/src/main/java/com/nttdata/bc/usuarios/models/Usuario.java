package com.nttdata.bc.usuarios.models;

import ch.qos.logback.classic.spi.LoggingEventVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "usuarios")
public class Usuario {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    @Indexed(unique = true)
    private String dni;
    private String nombreCompleto;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    @Indexed(unique = true)
    private String email;
    private boolean emailValidado;
    private String password;
    private String direccion;
    @Indexed(unique = true)
    private String telefono;
    private boolean telefonoValidado;
    private String rol;
    private boolean activo;

}
