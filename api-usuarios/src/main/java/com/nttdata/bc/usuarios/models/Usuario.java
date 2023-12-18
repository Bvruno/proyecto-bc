package com.nttdata.bc.usuarios.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document(collection = "usuarios")
public class Usuario {
    @Id
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
