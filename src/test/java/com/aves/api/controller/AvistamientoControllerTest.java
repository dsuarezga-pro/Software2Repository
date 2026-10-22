package com.aves.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print; // Importación mágica agregada

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AvistamientoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	// 1. POST - Éxito (201) y verificación de respuesta completa
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

		mockMvc.perform(post("/avistamientos").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.especie").value("Colibrí"))
				.andExpect(jsonPath("$.lugar").value("Jardín Botánico"));
	}

	// 2. POST - Error por regla de negocio (400)
	@Test
	void deberiaRetornar400SiFaltanDatos() throws Exception {
		String jsonInvalido = """
				{
				    "especie": "Pi",
				    "lugar": "",
				    "fecha": "2050-10-10",
				    "observador": ""
				}
				""";

		mockMvc.perform(post("/avistamientos").contentType(MediaType.APPLICATION_JSON).content(jsonInvalido))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	// 3. GET - Todos (200)
	@Test
	void deberiaListarTodosLosAvistamientos() throws Exception {
		mockMvc.perform(get("/avistamientos")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}

	// 4. GET - No encontrado (404)
	@Test
	void deberiaRetornar404SiAvistamientoNoExiste() throws Exception {
		mockMvc.perform(get("/avistamientos/999")).andDo(print()).andExpect(status().isNotFound());
	}

	// 5. PUT - Éxito (200)
	@Test
	void deberiaActualizarAvistamientoYRetornar200() throws Exception {
		String jsonCrear = "{\"especie\": \"Loro\", \"lugar\": \"Selva\", \"fecha\": \"2026-01-01\", \"observador\": \"Ana\"}";

		org.springframework.test.web.servlet.MvcResult result = mockMvc
				.perform(post("/avistamientos").contentType(MediaType.APPLICATION_JSON).content(jsonCrear)).andReturn();
		Integer idDinámico = com.jayway.jsonpath.JsonPath.read(result.getResponse().getContentAsString(), "$.id");

		String jsonActualizar = "{\"especie\": \"Guacamaya\", \"lugar\": \"Selva\", \"fecha\": \"2026-01-02\", \"observador\": \"Ana\"}";

		mockMvc.perform(
				put("/avistamientos/" + idDinámico).contentType(MediaType.APPLICATION_JSON).content(jsonActualizar))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.especie").value("Guacamaya"));
	}

	// 6. DELETE - Éxito (204) y verificación del efecto
	@Test
	void deberiaEliminarAvistamientoYComprobarEfecto404() throws Exception {
		String jsonCrear = "{\"especie\": \"Pato\", \"lugar\": \"Lago\", \"fecha\": \"2026-01-01\", \"observador\": \"Luis\"}";

		org.springframework.test.web.servlet.MvcResult result = mockMvc
				.perform(post("/avistamientos").contentType(MediaType.APPLICATION_JSON).content(jsonCrear)).andReturn();
		Integer idDinámico = com.jayway.jsonpath.JsonPath.read(result.getResponse().getContentAsString(), "$.id");

		mockMvc.perform(delete("/avistamientos/" + idDinámico)).andDo(print()).andExpect(status().isNoContent());

		mockMvc.perform(get("/avistamientos/" + idDinámico)).andDo(print()).andExpect(status().isNotFound());
	}
}