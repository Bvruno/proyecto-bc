package com.nttdata.bc.compras.exceptions;

public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String usuario, double montoTotal) {
        super("El usuario "+ usuario + " no tiene saldo suficiente, monto actual: " + montoTotal);
    }
}
