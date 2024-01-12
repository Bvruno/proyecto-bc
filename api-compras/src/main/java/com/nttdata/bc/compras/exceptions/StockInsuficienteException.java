package com.nttdata.bc.compras.exceptions;

public class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String message) {
        super("El stock de " + message + " es insuficiente");
    }
}
