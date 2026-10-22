package com.aves.api.util;

import com.aves.api.dto.AvistamientoDTO;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDeAvistamiento {

	private final Reloj reloj;

	public ValidadorDeAvistamiento(Reloj reloj) {
		this.reloj = reloj;
	}

	public void validar(AvistamientoDTO dto) {
		if (dto.getEspecie() == null || dto.getEspecie().trim().length() < 3 || dto.getEspecie().trim().length() > 50) {
			throw new IllegalArgumentException("La especie debe tener entre 3 y 50 caracteres.");
		}
		if (dto.getLugar() == null || dto.getLugar().trim().isEmpty()) {
			throw new IllegalArgumentException("El lugar no puede estar vacío.");
		}
		if (dto.getObservador() == null || dto.getObservador().trim().isEmpty()) {
			throw new IllegalArgumentException("El observador no puede estar vacío.");
		}

		if (dto.getFecha() == null) {
			throw new IllegalArgumentException("El formato de fecha debe ser AAAA-MM-DD y no estar vacía.");
		}

		if (dto.getFecha().isAfter(reloj.hoy())) {
			throw new IllegalArgumentException("La fecha no puede ser posterior a hoy.");
		}
	}
}