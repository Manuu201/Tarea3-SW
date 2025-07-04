package com.fidelidad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CompraRepositoryTest {

    private CompraRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CompraRepository();
    }

    @Test
    void testCrearCompra() {
        Compra compra = new Compra("c1", "cliente1", 1000, LocalDate.now());
        repository.crear(compra);

        Optional<Compra> encontrada = repository.buscarPorId("c1");
        assertTrue(encontrada.isPresent());
        assertEquals("cliente1", encontrada.get().getIdCliente());
    }

    @Test
    void testListarCompras() {
        repository.crear(new Compra("c1", "cliente1", 1000, LocalDate.now()));
        repository.crear(new Compra("c2", "cliente2", 2000, LocalDate.now()));

        List<Compra> compras = repository.listar();
        assertEquals(2, compras.size());
    }

    @Test
    void testActualizarCompra() {
        Compra original = new Compra("c1", "cliente1", 1000, LocalDate.of(2024, 6, 1));
        repository.crear(original);

        Compra actualizada = new Compra("c1", "cliente1", 3000, LocalDate.of(2024, 6, 2));
        repository.actualizar(actualizada);

        Optional<Compra> result = repository.buscarPorId("c1");
        assertTrue(result.isPresent());
        assertEquals(3000, result.get().getMonto());
        assertEquals(LocalDate.of(2024, 6, 2), result.get().getFecha());
    }

    @Test
    void testEliminarCompra() {
        Compra compra = new Compra("c1", "cliente1", 1000, LocalDate.now());
        repository.crear(compra);

        repository.eliminar("c1");

        Optional<Compra> result = repository.buscarPorId("c1");
        assertFalse(result.isPresent());
    }

    @Test
    void testBuscarCompraInexistente() {
        Optional<Compra> result = repository.buscarPorId("noExiste");
        assertFalse(result.isPresent());
    }
}
