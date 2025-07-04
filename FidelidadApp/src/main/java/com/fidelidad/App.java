package com.fidelidad;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {

    private static Scanner scanner = new Scanner(System.in);
    public static void setScanner(InputStream input) {
        scanner = new Scanner(input);
    }
    private static final ClienteRepository clienteRepo = new ClienteRepository();
    private static final CompraRepository compraRepo = new CompraRepository();
    private static final GestorCompras gestor = new GestorCompras(clienteRepo, compraRepo);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> registrarCliente();
                case "2" -> listarClientes();
                case "3" -> registrarCompra();
                case "4" -> mostrarPuntajeCliente();
                case "5" -> {
                    System.out.println("Gracias por usar FidelidadApp. ¡Hasta pronto!");
                    salir = true;
                }
                default -> System.out.println("❌ Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("""
                \n==== MENÚ PRINCIPAL ====
                1. Registrar nuevo cliente
                2. Listar clientes
                3. Registrar compra
                4. Ver puntos y nivel de un cliente
                5. Salir
                Seleccione una opción:
                """);
    }

    private static void registrarCliente() {
        System.out.print("Ingrese ID del cliente: ");
        String id = scanner.nextLine();

        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese correo: ");
        String correo = scanner.nextLine();

        try {
            Cliente nuevo = new Cliente(id, nombre, correo);
            clienteRepo.crear(nuevo);
            System.out.println("✅ Cliente registrado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void listarClientes() {
        List<Cliente> clientes = clienteRepo.listar();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println("\n==== Lista de Clientes ====");
        for (Cliente c : clientes) {
            System.out.printf("ID: %s | Nombre: %s | Correo: %s | Puntos: %d | Nivel: %s\n",
                    c.getId(), c.getNombre(), c.getCorreo(), c.getPuntos(), c.getNivel());
        }
    }

    private static void registrarCompra() {
        System.out.print("Ingrese ID de la compra: ");
        String idCompra = scanner.nextLine();

        System.out.print("Ingrese ID del cliente: ");
        String idCliente = scanner.nextLine();

        System.out.print("Ingrese monto de la compra: ");
        int monto;
        try {
            monto = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Monto inválido.");
            return;
        }

        System.out.print("Ingrese fecha de la compra (YYYY-MM-DD): ");
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(scanner.nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("❌ Fecha inválida.");
            return;
        }

        try {
            gestor.registrarCompra(idCompra, idCliente, monto, fecha);
            System.out.println("✅ Compra registrada con éxito.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void mostrarPuntajeCliente() {
        System.out.print("Ingrese ID del cliente: ");
        String id = scanner.nextLine();

        Optional<Cliente> cliente = clienteRepo.buscarPorId(id);
        if (cliente.isPresent()) {
            Cliente c = cliente.get();
            System.out.printf("\nCliente: %s\nPuntos: %d\nNivel: %s\n",
                    c.getNombre(), c.getPuntos(), c.getNivel());
        } else {
            System.out.println("❌ Cliente no encontrado.");
        }
    }
}
