package com.fidelidad;
import com.fidelidad.ClienteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClienteRepositoryTest {

    private ClienteRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ClienteRepository();
    }

    @Test
    void testCrearCliente() {
        Cliente cliente = new Cliente("1", "Ana", "ana@mail.com");
        repository.crear(cliente);

        Optional<Cliente> encontrado = repository.buscarPorId("1");
        assertTrue(encontrado.isPresent());
        assertEquals("Ana", encontrado.get().getNombre());
    }

    @Test
    void testListarClientes() {
        repository.crear(new Cliente("1", "Ana", "ana@mail.com"));
        repository.crear(new Cliente("2", "Luis", "luis@mail.com"));

        List<Cliente> lista = repository.listar();
        assertEquals(2, lista.size());
    }

    @Test
    void testActualizarCliente() {
        Cliente cliente = new Cliente("1", "Ana", "ana@mail.com");
        repository.crear(cliente);

        Cliente actualizado = new Cliente("1", "Ana María", "ana.mar@mail.com");
        repository.actualizar(actualizado);

        Optional<Cliente> result = repository.buscarPorId("1");
        assertTrue(result.isPresent());
        assertEquals("Ana María", result.get().getNombre());
        assertEquals("ana.mar@mail.com", result.get().getCorreo());
    }

    @Test
    void testEliminarCliente() {
        Cliente cliente = new Cliente("1", "Ana", "ana@mail.com");
        repository.crear(cliente);
        repository.eliminar("1");

        Optional<Cliente> result = repository.buscarPorId("1");
        assertFalse(result.isPresent());
    }

    @Test
    void testBuscarClienteInexistente() {
        Optional<Cliente> result = repository.buscarPorId("no-existe");
        assertFalse(result.isPresent());
    }
}
