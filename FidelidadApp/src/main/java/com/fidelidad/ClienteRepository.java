package com.fidelidad;

import com.fidelidad.Cliente;

import java.util.*;

public class ClienteRepository {

    private final Map<String, Cliente> clientes;

    public ClienteRepository() {
        this.clientes = new HashMap<>();
    }

    public void crear(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }

    public List<Cliente> listar() {
        return new ArrayList<>(clientes.values());
    }

    public Optional<Cliente> buscarPorId(String id) {
        return Optional.ofNullable(clientes.get(id));
    }

    public void actualizar(Cliente clienteActualizado) {
        if (clientes.containsKey(clienteActualizado.getId())) {
            clientes.put(clienteActualizado.getId(), clienteActualizado);
        }
    }

    public void eliminar(String id) {
        clientes.remove(id);
    }
}
