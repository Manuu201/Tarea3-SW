package com.fidelidad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GestorComprasTest {

    private ClienteRepository clienteRepo;
    private CompraRepository compraRepo;
    private GestorCompras gestor;

    @BeforeEach
    void setUp() {
        clienteRepo = new ClienteRepository();
        compraRepo = new CompraRepository();
        gestor = new GestorCompras(clienteRepo, compraRepo);
    }

    @Test
    void testCompraClienteBronceOtorgaPuntosBase() {
        Cliente cliente = new Cliente("1", "Ana", "ana@mail.com");
        clienteRepo.crear(cliente);

        gestor.registrarCompra("compra1", "1", 1000, LocalDate.of(2024, 6, 21));

        Optional<Cliente> actualizado = clienteRepo.buscarPorId("1");
        assertTrue(actualizado.isPresent());
        assertEquals(10, actualizado.get().getPuntos());  // $1000 → 10 pts
        assertEquals(Nivel.BRONCE, actualizado.get().getNivel());
    }

    @Test
    void testCompraClienteOroOtorgaPuntosConMultiplicador() {
        Cliente cliente = new Cliente("2", "Luis", "luis@mail.com");

        // ⚠️ Método directo para test: simular cliente ORO
        setPuntosYRecalcularNivel(cliente, 1500);

        assertEquals(Nivel.ORO, cliente.getNivel()); // Asegura precondición
        int puntosAntes = cliente.getPuntos();

        clienteRepo.crear(cliente);

        gestor.registrarCompra("compra2", "2", 1000, LocalDate.of(2024, 6, 21));

        Optional<Cliente> actualizado = clienteRepo.buscarPorId("2");
        assertTrue(actualizado.isPresent());
        assertEquals(Nivel.ORO, actualizado.get().getNivel());

        int puntosEsperados = puntosAntes + 15; // 1000 → 10 × 1.5 = 15
        assertEquals(puntosEsperados, actualizado.get().getPuntos());
    }

    private void setPuntosYRecalcularNivel(Cliente cliente, int puntos) {
        // ⚠️ método auxiliar usando reflexión (test-only hack)
        try {
            var field = Cliente.class.getDeclaredField("puntos");
            field.setAccessible(true);
            field.setInt(cliente, puntos);

            var metodo = Cliente.class.getDeclaredMethod("recalcularNivel");
            metodo.setAccessible(true);
            metodo.invoke(cliente);
        } catch (Exception e) {
            fail("No se pudo forzar el estado del cliente: " + e.getMessage());
        }
    }



    @Test
    void testTercerCompraDelDiaOtorgaBonus() {
        Cliente cliente = new Cliente("3", "Mario", "mario@mail.com");
        clienteRepo.crear(cliente);

        LocalDate hoy = LocalDate.of(2024, 6, 21);

        gestor.registrarCompra("c1", "3", 1000, hoy); // compra 1
        gestor.registrarCompra("c2", "3", 1000, hoy); // compra 2
        gestor.registrarCompra("c3", "3", 1000, hoy); // compra 3 → 🎁 bonus

        Optional<Cliente> actualizado = clienteRepo.buscarPorId("3");
        assertTrue(actualizado.isPresent());
        assertEquals(40, actualizado.get().getPuntos()); // 3×10 + bonus 10 = 40
    }

    @Test
    void testSeRegistraCompraEnRepositorio() {
        Cliente cliente = new Cliente("4", "Tina", "tina@mail.com");
        clienteRepo.crear(cliente);

        gestor.registrarCompra("c100", "4", 900, LocalDate.now());

        Optional<Compra> compra = compraRepo.buscarPorId("c100");
        assertTrue(compra.isPresent());
        assertEquals("4", compra.get().getIdCliente());
        assertEquals(900, compra.get().getMonto());
    }
}
