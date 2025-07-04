package com.fidelidad;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    public void clienteSeCreaConValoresInicialesCorrectos() {
        Cliente cliente = new Cliente("001", "Manuel Vargas", "manuel@mail.com");

        assertEquals("001", cliente.getId());
        assertEquals("Manuel Vargas", cliente.getNombre());
        assertEquals("manuel@mail.com", cliente.getCorreo());
        assertEquals(0, cliente.getPuntos());
        assertEquals(Nivel.BRONCE, cliente.getNivel());
        assertEquals(0, cliente.getStreakDias());
    }

    @Test
    public void clienteConCorreoInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cliente("002", "Manuel", "correoSinArroba");
        });
    }

    @Test
    public void clienteBronceSumaPuntosBaseSinMultiplicador() {
        Cliente cliente = new Cliente("003", "Luis Soto", "luis@mail.com");

        cliente.registrarCompra(250, LocalDate.of(2025, 6, 21));

        assertEquals(2, cliente.getPuntos());
        assertEquals(Nivel.BRONCE, cliente.getNivel());
    }

    @Test
    public void clienteSubeDeNivelConPuntosAcumulados() {
        Cliente cliente = new Cliente("004", "Ana Pérez", "ana@mail.com");

        cliente.registrarCompra(50000, LocalDate.of(2025, 6, 21));

        assertEquals(500, cliente.getPuntos());
        assertEquals(Nivel.PLATA, cliente.getNivel());
    }

    @Test
    public void clienteRecibeBonusPorTresComprasElMismoDia() {
        Cliente cliente = new Cliente("005", "Sofía Herrera", "sofia@mail.com");

        LocalDate hoy = LocalDate.of(2025, 6, 21);

        cliente.registrarCompra(100, hoy);
        cliente.registrarCompra(100, hoy);
        cliente.registrarCompra(100, hoy);

        assertEquals(13, cliente.getPuntos());
    }
}
