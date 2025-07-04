package com.fidelidad;

import java.util.*;

public class CompraRepository {

    private final Map<String, Compra> compras;

    public CompraRepository() {
        this.compras = new HashMap<>();
    }

    public void crear(Compra compra) {
        compras.put(compra.getIdCompra(), compra);
    }

    public List<Compra> listar() {
        return new ArrayList<>(compras.values());
    }

    public Optional<Compra> buscarPorId(String idCompra) {
        return Optional.ofNullable(compras.get(idCompra));
    }

    public void actualizar(Compra compraActualizada) {
        if (compras.containsKey(compraActualizada.getIdCompra())) {
            compras.put(compraActualizada.getIdCompra(), compraActualizada);
        }
    }

    public void eliminar(String idCompra) {
        compras.remove(idCompra);
    }
}
