package com.nttdata.bc.compras.exceptions;

public class UsuarioNoEncontradoException extends Exception {
    public UsuarioNoEncontradoException() {
        super("No se encontró el usuario");
    }
}
