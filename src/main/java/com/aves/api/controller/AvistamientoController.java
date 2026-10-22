package com.aves.api.controller;

import com.aves.api.dto.AvistamientoDTO;
import com.aves.api.dto.ResumenEspecieDTO;
import com.aves.api.service.AvistamientoService;
import com.aves.api.util.ValidadorDeAvistamiento;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/avistamientos")
public class AvistamientoController {

	private final AvistamientoService service;
	private final ValidadorDeAvistamiento validador; // Se agrega el validador

	// Se inyecta el validador en el constructor
	public AvistamientoController(AvistamientoService service, ValidadorDeAvistamiento validador) {
		this.service = service;
		this.validador = validador;
	}

	// GET /avistamientos (Lista todos - Código 200)
	@GetMapping
	public ResponseEntity<List<AvistamientoDTO>> obtenerTodos() {
		return ResponseEntity.ok(service.obtenerTodos());
	}

	// GET /avistamientos/{id} (Ver uno - Código 200 o 404)
	@GetMapping("/{id}")
	public ResponseEntity<AvistamientoDTO> obtenerPorId(@PathVariable Long id) {
		return ResponseEntity.ok(service.obtenerPorId(id));
	}

	// POST /avistamientos (Crear - Código 201 o 400)
	@PostMapping
	public ResponseEntity<AvistamientoDTO> crear(@Valid @RequestBody AvistamientoDTO dto) {
		// 1. Llamamos a la regla de negocio para validar los datos
		validador.validar(dto);

		// 2. Si todo es correcto, se crea el avistamiento
		return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
	}

	// PUT /avistamientos/{id} (Actualizar - Código 200 o 404)
	@PutMapping("/{id}")
	public ResponseEntity<AvistamientoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody AvistamientoDTO dto) {
		// 1. Llamamos a la regla de negocio para validar los datos
		validador.validar(dto);

		// 2. Si todo es correcto, se actualiza el avistamiento
		return ResponseEntity.ok(service.actualizar(id, dto));
	}

	// DELETE /avistamientos/{id} (Eliminar - Código 204 o 404)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		service.eliminar(id);
		return ResponseEntity.noContent().build();
	}

	// GET /avistamientos/resumen (Punto Bonus - Código 200)
	@GetMapping("/resumen")
	public ResponseEntity<List<ResumenEspecieDTO>> obtenerResumen() {
		return ResponseEntity.ok(service.obtenerResumenPorEspecie());
	}

	// Manejador de excepciones: atrapa los errores del validador y devuelve 400 Bad
	// Request
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> manejarValidacion(IllegalArgumentException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}