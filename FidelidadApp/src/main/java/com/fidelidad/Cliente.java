package com.fidelidad;

import java.time.LocalDate;

public class Cliente {
    private String id;
    private String nombre;
    private String correo;
    private int puntos;
    private Nivel nivel;
    private int streakDias;
    private LocalDate fechaUltimaCompra;
    private int comprasHoy;

    public Cliente(String id, String nombre, String correo) {
        if (!correo.contains("@")) {
            throw new IllegalArgumentException("Correo inválido");
        }

        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.puntos = 0;
        this.nivel = Nivel.BRONCE;
        this.streakDias = 0;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public int getPuntos() {
        return puntos;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public int getStreakDias() {
        return streakDias;
    }

    public void registrarCompra(int monto, LocalDate fecha) {
        if (fechaUltimaCompra == null || !fechaUltimaCompra.equals(fecha)) {
            fechaUltimaCompra = fecha;
            comprasHoy = 1;
        } else {
            comprasHoy++;
        }

        int puntosBase = monto / 100;

        double multiplicador = switch (nivel) {
            case BRONCE -> 1.0;
            case PLATA -> 1.2;
            case ORO -> 1.5;
            case PLATINO -> 2.0;
        };

        int puntosGanados = (int) Math.floor(puntosBase * multiplicador);

        // 🎁 Bonus si se alcanza la tercera compra del día
        if (comprasHoy == 3) {
            puntosGanados += 10;
        }

        this.puntos += puntosGanados;

        recalcularNivel();
    }


    private void recalcularNivel() {
        if (puntos >= 3000) {
            nivel = Nivel.PLATINO;
        } else if (puntos >= 1500) {
            nivel = Nivel.ORO;
        } else if (puntos >= 500) {
            nivel = Nivel.PLATA;
        } else {
            nivel = Nivel.BRONCE;
        }
    }
}
