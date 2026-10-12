package com.aves.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AvistamientoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void deberiaCrearAvistamientoYRetornar201() throws Exception {
		String json = """
				{
				    "especie": "Colibrí",
				    "lugar": "Jardín Botánico",
				    "fecha": "2026-10-10",
				    "observador": "Juan Pérez"
				}
				""";

		mockMvc.perform(post("/avistamientos").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated()) // Verifica el código 201
				.andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.especie").value("Colibrí"));
	}

	@Test
	void deberiaRetornar400SiFaltanDatos() throws Exception {
		// JSON sin lugar, fecha ni observador para forzar el error de validación
		String jsonInvalido = "{\"especie\": \"\"}";

		mockMvc.perform(post("/avistamientos").contentType(MediaType.APPLICATION_JSON).content(jsonInvalido))
				.andExpect(status().isBadRequest()); // Verifica el código 400
	}

	@Test
	void deberiaRetornar404SiAvistamientoNoExiste() throws Exception {
		mockMvc.perform(get("/avistamientos/999")).andExpect(status().isNotFound()); // Verifica el código 404
	}
}