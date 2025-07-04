package com.fidelidad;

import java.time.LocalDate;

public class Compra {
    private String idCompra;
    private String idCliente;
    private int monto;
    private LocalDate fecha;

    public Compra(String idCompra, String idCliente, int monto, LocalDate fecha) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public int getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
