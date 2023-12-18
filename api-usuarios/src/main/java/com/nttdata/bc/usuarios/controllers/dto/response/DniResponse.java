package com.nttdata.bc.usuarios.controllers.dto.response;

import lombok.Data;

@Data
public class DniResponse {
    private String nombre;
    private String tipoDocumento;
    private String numeroDocumento;
    private String estado;
    private String condicion;
    private String direccion;
    private String ubigeo;
    private String viaTipo;
    private String viaNombre;
    private String zonaCodigo;
    private String zonaTipo;
    private String numero;
    private String interior;
    private String lote;
    private String dpto;
    private String manzana;
    private String kilometro;
    private String distrito;
    private String provincia;
    private String departamento;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
}

