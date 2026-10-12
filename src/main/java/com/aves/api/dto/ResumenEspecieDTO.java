package com.aves.api.dto;

public class ResumenEspecieDTO {

	private String especie;
	private Long total;

	public ResumenEspecieDTO(String especie, Long total) {
		this.especie = especie;
		this.total = total;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}