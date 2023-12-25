package com.nttdata.bc.compras.exceptions;

public class StockInsuficienteException extends Exception {
    public StockInsuficienteException() {
        super("El stock de uno o más productos es insuficiente");
    }
}
