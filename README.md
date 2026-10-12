# API REST - Registro de Avistamientos de Aves

Esta es una API REST desarrollada con Spring Boot y H2 Database (en memoria) para gestionar el registro de aves observadas en el campo.

**Autor:** David Alejandro Suarez Gañan  
**Programa:** Ingeniería de Sistemas, Universidad El Bosque

## Requisitos
* Java 17 o superior
* Maven

## Instrucciones de Ejecución
1. Clonar este repositorio.
2. Abrir una terminal en la raíz del proyecto.
3. Ejecutar el siguiente comando de Maven para iniciar la aplicación:

    mvn spring-boot:run

4. La API estará disponible en `http://localhost:8081/avistamientos`.
5. *(Opcional)* La consola de la base de datos H2 se puede acceder en `http://localhost:8081/h2-console` (Usuario: `sa`, sin contraseña).

## Endpoints y Ejemplos de Uso (cURL)

### 1. Registrar un avistamiento (POST)
Crea un nuevo registro. Retorna código `201` si es exitoso, o `400` si faltan datos obligatorios.

    curl -X POST http://localhost:8081/avistamientos -H "Content-Type: application/json" -d "{\"especie\":\"Colibrí\",\"lugar\":\"Cerro de Monserrate\",\"fecha\":\"2026-10-11\",\"observador\":\"Juan Pérez\"}"

### 2. Listar todos los avistamientos (GET)
Retorna código `200` con la lista de avistamientos en formato JSON.

    curl -X GET http://localhost:8081/avistamientos

### 3. Ver un avistamiento específico (GET)
Retorna código `200` con los datos del avistamiento, o `404` si el ID no existe.

    curl -X GET http://localhost:8081/avistamientos/1

### 4. Actualizar un avistamiento (PUT)
Modifica un registro existente. Retorna código `200` si es exitoso, o `404` si el ID no existe.

    curl -X PUT http://localhost:8081/avistamientos/1 -H "Content-Type: application/json" -d "{\"especie\":\"Colibrí\",\"lugar\":\"Jardín Botánico\",\"fecha\":\"2026-10-11\",\"observador\":\"Juan Pérez\"}"

### 5. Eliminar un avistamiento (DELETE)
Elimina un registro por su ID. Retorna código `204` (No Content) si es exitoso, o `404` si el ID no existe.

    curl -X DELETE http://localhost:8081/avistamientos/1

### 6. Resumen por especie (Punto Bonus) (GET)
Devuelve la cantidad total de avistamientos agrupados por especie.

    curl -X GET http://localhost:8081/avistamientos/resumen

## Declaración de Uso de IA
En cumplimiento con las directrices de la entrega, declaro que utilicé el modelo de inteligencia artificial Gemini para recibir asistencia en la estructuración de la arquitectura del proyecto en Spring Boot y las validaciones de datos. Comprendo en su totalidad el código implementado y asumo la responsabilidad sobre el mismo.
