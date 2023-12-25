package com.nttdata.bc.compras.exceptions;

public class ProductoNoEncontradoException extends Exception {
    public ProductoNoEncontradoException() {
        super("No se encontró el producto");
    }
}
