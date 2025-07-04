# FidelidadApp – Sistema de Tarjeta de Fidelidad Gamificada

Proyecto de línea de comandos en **Java 21**, con arquitectura orientada a objetos, desarrollo guiado por pruebas (**TDD**) y medición de cobertura con **JaCoCo**.

---

## Descripción General

FidelidadApp es un sistema de fidelización para una cadena de tiendas. Permite:

- Registrar clientes.
- Registrar compras por cliente.
- Calcular puntos automáticamente.
- Subir de nivel (Bronce → Plata → Oro → Platino).
- Otorgar bonus por streak (3 compras el mismo día).
- Mostrar puntaje y nivel actual del cliente.

---

## ⚙Requisitos Técnicos

| Elemento              | Especificación                      |
|-----------------------|-------------------------------------|
| Lenguaje              | Java 21                             |
| Construcción          | Maven (`pom.xml` incluido)          |
| Pruebas Unitarias     | JUnit 5                             |
| Cobertura             | JaCoCo (Jacoco Maven Plugin)        |
| Interfaz              | Por consola                         |
| Estilo de diseño      | Clean Architecture + Modularización |
| Persistencia          | En memoria (`Map`)                  |

---

## Testeo y TDD

Todo el sistema fue desarrollado utilizando **Test-Driven Development (TDD)**. Cada clase tiene su archivo de pruebas correspondiente:

| Clase                | Archivo de Test                 |
|---------------------|----------------------------------|
| `Cliente`           | `ClienteTest.java`              |
| `ClienteRepository` | `ClienteRepositoryTest.java`    |
| `Compra`            | `CompraRepositoryTest.java`     |
| `GestorCompras`     | `GestorComprasTest.java`        |

---

## Niveles de Fidelidad

| Nivel    | Puntos Totales         | Multiplicador |
|----------|------------------------|----------------|
| Bronce   | 0 – 499                | ×1             |
| Plata    | 500 – 1499             | ×1.2           |
| Oro      | 1500 – 2999            | ×1.5           |
| Platino  | 3000+                  | ×2             |

Además:
- **3 compras el mismo día → Bonus +10 puntos**

---

## Diseño de Clases (Resumen UML textual)

- `Cliente`: id, nombre, correo, puntos, nivel, lógica de acumulación.
- `Compra`: idCompra, idCliente, monto, fecha.
- `ClienteRepository`: CRUD de clientes (`Map<String, Cliente>`).
- `CompraRepository`: CRUD de compras (`Map<String, Compra>`).
- `GestorCompras`: Servicio de aplicación que orquesta lógica de fidelidad.

---

## ¿Qué se ha implementado?

- [x] CRUD completo de Clientes con validación de correo.
- [x] CRUD completo de Compras.
- [x] Cálculo de puntos y bonus por streak.
- [x] Recalculador automático de nivel según puntos acumulados.
- [x] Integración lógica a través de `GestorCompras`.
- [x] Tests unitarios para cada componente.
- [x] Cobertura de código medida con JaCoCo.

---

## Cobertura de Código (JaCoCo)

La cobertura fue generada con:

```bash
mvn clean test
mvn jacoco:report
```

Ubicación del reporte:

```
target/site/jacoco/index.html
```

Resultado:

| Métrica               | Porcentaje |
|-----------------------|------------|
| Instrucciones         | 94%        |
| Ramas (if/switch)     | 70%        |
| Clases                | 7 de 7     |
| Métodos               | 29 de 31   |

> La única clase no testeada es `App.java`, correspondiente al menú por consola. Será probada funcionalmente.

---

## ▶Cómo Compilar y Ejecutar

### 1. Compilar el proyecto:

```bash
mvn clean compile
```

### 2. Ejecutar los tests:

```bash
mvn test
```

### 3. Generar el reporte de cobertura:

```bash
mvn jacoco:report
start target/site/jacoco/index.html
```

### 4. Ejecutar por consola (menú CLI, próximamente):

```bash
mvn exec:java -Dexec.mainClass="com.fidelidad.App"
```

---

## Estructura de Carpetas

```
FidelidadApp/
├── src/
│   ├── main/
│   │   └── java/com/fidelidad/
│   │       ├── Cliente.java
│   │       ├── Compra.java
│   │       ├── ClienteRepository.java
│   │       ├── CompraRepository.java
│   │       ├── GestorCompras.java
│   │       └── App.java (próxima etapa)
│   └── test/
│       └── java/com/fidelidad/
│           ├── ClienteTest.java
│           ├── ClienteRepositoryTest.java
│           ├── CompraRepositoryTest.java
│           ├── GestorComprasTest.java
│           └── AppTest.java
```

---

## Consideraciones Finales

- El proyecto aplica principios de diseño limpio.
- El sistema está desacoplado en repositorios, servicios y entidades.
- Todas las reglas de negocio están cubiertas por tests.
- La cobertura alta garantiza que la lógica central es robusta.

---

## ¿Qué tipo de cobertura he medido y por qué?

He medido cobertura de instrucciones y cobertura de ramas utilizando la herramienta JaCoCo.

La cobertura de instrucciones verifica qué porcentaje de las líneas ejecutables del código fueron realmente ejecutadas por los tests.

La cobertura de ramas analiza las estructuras de control (if, switch, etc.) y evalúa si se han probado todas las decisiones posibles (verdadero y falso).

Elegí medir estos dos tipos de cobertura porque proporcionan una visión clara y objetiva sobre qué tan bien los tests ejercen la lógica del sistema.
Son también las métricas recomendadas en proyectos Java con enfoque TDD, ya que aseguran no solo que se ejecuta el código, sino también que se toman en cuenta distintos caminos lógicos.

## Conclusiones finales 
El proyecto FidelidadApp alcanza una cobertura de instrucciones del 92% y una cobertura de ramas del 73% según el reporte generado con JaCoCo 0.8.11.

Esta alta cobertura indica que:

- La gran mayoría de las líneas de código fueron ejecutadas durante los tests (92%).
- La lógica condicional también ha sido cubierta de forma sólida (73%), aunque con margen de mejora.

Se testearon exitosamente las 7 clases principales, incluyendo los repositorios, la lógica de negocio y la entidad Cliente, con un total de 21 tests pasados y sin errores.

Esta cobertura es suficiente para validar la funcionalidad general del sistema y demuestra un uso adecuado de pruebas unitarias con JUnit 5.

Se puede seguir realizando pruebas pero ya se probo la gran mayoria de cosas, ademas se puede testear la app manualmente con el comando mvn clean compile exec:java 
y con mvn clean test verify se puede verificar las pruebas mostradas en el punto anterior. 

## Evidencia

Aqui esta toda la evidencia relacionada en caso de que no llegue a funcionar 

Pruebas realizadas
![Screenshot 2025-07-04 180956](https://github.com/user-attachments/assets/5c24a341-99ca-4e5b-b4a1-5af90c839330)

Menu simple implementado 
![Screenshot 2025-07-04 181024](https://github.com/user-attachments/assets/02bafba1-ec85-4d1a-8728-571ff9dbe2fb)

Cobertura de las pruebas
![Screenshot 2025-07-04 181935](https://github.com/user-attachments/assets/5f55abe4-befc-428a-9cfe-70580b1ecfcf)




## Licencia

Este proyecto fue desarrollado como parte de una tarea académica y no posee una licencia de distribución pública.

---
