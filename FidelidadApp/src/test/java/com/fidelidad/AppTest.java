
package com.fidelidad;

import org.junit.jupiter.api.Test;
import com.fidelidad.App;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testFlujoBasicoMenu() {
        String inputSimulado = String.join(System.lineSeparator(),
                "1",            // Registrar cliente
                "001",          // ID
                "Juan",         // Nombre
                "juan@mail.com",// Correo
                "2",            // Listar clientes
                "5"             // Salir
        );

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayInputStream entrada = new ByteArrayInputStream(inputSimulado.getBytes());
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PrintStream salidaCapturada = new PrintStream(salida);

        try {
            System.setIn(entrada);
            System.setOut(salidaCapturada);
            App.setScanner(entrada);
            App.main(new String[]{}); // Ejecuta el menú como si fuera usuario

        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String output = salida.toString();

        assertTrue(output.contains("Cliente registrado correctamente"));
        assertTrue(output.contains("Juan"));
        assertTrue(output.contains("juan@mail.com"));
        assertTrue(output.contains("MENÚ PRINCIPAL"));
    }

    @Test
    void testRegistroCompraConBonusPorStreak() {
        StringBuilder inputSimulado = new StringBuilder();
        inputSimulado.append("1\n123\nTest Bonus\nbonus@mail.com\n");  // Registrar cliente
        inputSimulado.append("3\nc1\n123\n1000\n2024-06-21\n");       // Compra 1
        inputSimulado.append("3\nc2\n123\n1000\n2024-06-21\n");       // Compra 2
        inputSimulado.append("3\nc3\n123\n1000\n2024-06-21\n");       // Compra 3 (bonus)
        inputSimulado.append("4\n123\n");                                // Consultar puntos
        inputSimulado.append("5\n\n");                                   // Salir

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayInputStream entrada = new ByteArrayInputStream(inputSimulado.toString().replace("\n", System.lineSeparator()).getBytes());
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PrintStream salidaCapturada = new PrintStream(salida);

        try {
            System.setIn(entrada);
            System.setOut(salidaCapturada);
            App.setScanner(entrada);  // ✅ Línea agregada
            App.main(new String[]{});
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String output = salida.toString();

        assertTrue(output.contains("Puntos: 40"), "❌ No se aplicó bonus por streak correctamente");
        assertTrue(output.contains("Test Bonus"), "❌ Cliente no fue registrado correctamente");
    }
}
