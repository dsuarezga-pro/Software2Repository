package com.aves.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvistamientoDTO {

	private Long id;

	// Estas anotaciones @NotBlank y @NotNull garantizan el error 400 si faltan
	// datos
	@NotBlank(message = "La especie es obligatoria")
	private String especie;

	@NotBlank(message = "El lugar es obligatorio")
	private String lugar;

	@NotNull(message = "La fecha es obligatoria")
	private LocalDate fecha; // El enunciado exige formato AAAA-MM-DD

	@NotBlank(message = "El observador es obligatorio")
	private String observador;

	public AvistamientoDTO() {
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getObservador() {
		return observador;
	}

	public void setObservador(String observador) {
		this.observador = observador;
	}
}