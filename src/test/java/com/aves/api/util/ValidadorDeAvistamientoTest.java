package com.aves.api.util;

import com.aves.api.dto.AvistamientoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidadorDeAvistamientoTest {

	private Reloj relojStub;
	private ValidadorDeAvistamiento validador;
	private AvistamientoDTO dto;
	private final LocalDate HOY = LocalDate.of(2026, 10, 21);

	@BeforeEach
	void setUp() {
		relojStub = mock(Reloj.class);
		when(relojStub.hoy()).thenReturn(HOY);

		validador = new ValidadorDeAvistamiento(relojStub);

		dto = new AvistamientoDTO();
		dto.setEspecie("Colibrí");
		dto.setLugar("Bogotá");
		dto.setObservador("David");
		dto.setFecha(HOY);
	}

	@Test
	void validar_EspecieDe2Caracteres_LanzaExcepcion() {
		dto.setEspecie("Pi");
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			validador.validar(dto);
		});
		assertTrue(exception.getMessage().contains("entre 3 y 50 caracteres"));
	}

	@Test
	void validar_EspecieDe3Caracteres_NoLanzaExcepcion() {
		dto.setEspecie("Pez");
		assertDoesNotThrow(() -> validador.validar(dto));
	}

	@Test
	void validar_EspecieDe50Caracteres_NoLanzaExcepcion() {
		String especie50 = "A".repeat(50);
		dto.setEspecie(especie50);
		assertDoesNotThrow(() -> validador.validar(dto));
	}

	@Test
	void validar_EspecieDe51Caracteres_LanzaExcepcion() {
		String especie51 = "A".repeat(51);
		dto.setEspecie(especie51);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			validador.validar(dto);
		});
		assertTrue(exception.getMessage().contains("entre 3 y 50 caracteres"));
	}

	@Test
	void validar_FechaIgualAHoy_NoLanzaExcepcion() {
		dto.setFecha(HOY);
		assertDoesNotThrow(() -> validador.validar(dto));
	}

	@Test
	void validar_FechaDeManana_LanzaExcepcion() {
		LocalDate manana = HOY.plusDays(1);
		dto.setFecha(manana);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			validador.validar(dto);
		});
		assertTrue(exception.getMessage().contains("posterior a hoy"));
	}

	@Test
	void validar_LugarVacio_LanzaExcepcion() {
		dto.setLugar("   ");
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			validador.validar(dto);
		});
		assertTrue(exception.getMessage().contains("El lugar no puede estar vacío"));
	}

	@Test
	void validar_ObservadorVacio_LanzaExcepcion() {
		dto.setObservador("");
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			validador.validar(dto);
		});
		assertTrue(exception.getMessage().contains("El observador no puede estar vacío"));
	}
}