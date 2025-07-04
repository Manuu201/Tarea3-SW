package com.fidelidad;

import java.time.LocalDate;
import java.util.Optional;

public class GestorCompras {

    private final ClienteRepository clienteRepo;
    private final CompraRepository compraRepo;

    public GestorCompras(ClienteRepository clienteRepo, CompraRepository compraRepo) {
        this.clienteRepo = clienteRepo;
        this.compraRepo = compraRepo;
    }

    public void registrarCompra(String idCompra, String idCliente, int monto, LocalDate fecha) {
        Optional<Cliente> optCliente = clienteRepo.buscarPorId(idCliente);
        if (optCliente.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado: " + idCliente);
        }

        Cliente cliente = optCliente.get();

        // Lógica de puntos y nivel
        cliente.registrarCompra(monto, fecha);

        // Actualiza cliente en el repositorio
        clienteRepo.actualizar(cliente);

        // Guarda la compra
        Compra compra = new Compra(idCompra, idCliente, monto, fecha);
        compraRepo.crear(compra);
    }
}
