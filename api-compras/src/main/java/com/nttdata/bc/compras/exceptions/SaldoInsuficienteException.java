package com.nttdata.bc.compras.exceptions;

public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException() {
        super("El usuario no tiene saldo suficiente");
    }
}
