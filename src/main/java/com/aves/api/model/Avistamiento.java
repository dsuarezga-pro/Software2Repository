package com.aves.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.time.LocalDate;

@Entity
@Table(name = "avistamientos")
public class Avistamiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String especie;

	@Column(nullable = false)
	private String lugar;

	@Column(nullable = false)
	private LocalDate fecha; // Formato AAAA-MM-DD

	@Column(nullable = false)
	private String observador;

	// Constructor vacío requerido por JPA
	public Avistamiento() {
	}

	// Constructor con campos
	public Avistamiento(Long id, String especie, String lugar, LocalDate fecha, String observador) {
		this.id = id;
		this.especie = especie;
		this.lugar = lugar;
		this.fecha = fecha;
		this.observador = observador;
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