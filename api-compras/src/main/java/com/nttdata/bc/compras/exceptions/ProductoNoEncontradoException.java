package com.nttdata.bc.compras.exceptions;

public class ProductoNoEncontradoException extends Exception {
    public ProductoNoEncontradoException(String message) {
        super("No se encontró el producto con id: " + message + " en el sistema");
    }
}
