package com.aves.api.service;

import com.aves.api.dto.AvistamientoDTO;
import com.aves.api.dto.ResumenEspecieDTO;
import com.aves.api.exception.RecursoNoEncontradoException;
import com.aves.api.model.Avistamiento;
import com.aves.api.repository.AvistamientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvistamientoService {

	private final AvistamientoRepository repository;

	public AvistamientoService(AvistamientoRepository repository) {
		this.repository = repository;
	}

	public List<AvistamientoDTO> obtenerTodos() {
		return repository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
	}

	public AvistamientoDTO obtenerPorId(Long id) {
		Avistamiento entidad = repository.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Avistamiento no encontrado con ID: " + id));
		return convertirADTO(entidad);
	}

	public AvistamientoDTO crear(AvistamientoDTO dto) {
		Avistamiento entidad = convertirAEntidad(dto);
		entidad.setId(null); // Asegura que sea un registro nuevo
		return convertirADTO(repository.save(entidad));
	}

	public AvistamientoDTO actualizar(Long id, AvistamientoDTO dto) {
		Avistamiento existente = repository.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Avistamiento no encontrado con ID: " + id));

		existente.setEspecie(dto.getEspecie());
		existente.setLugar(dto.getLugar());
		existente.setFecha(dto.getFecha());
		existente.setObservador(dto.getObservador());

		return convertirADTO(repository.save(existente));
	}

	public void eliminar(Long id) {
		if (!repository.existsById(id)) {
			throw new RecursoNoEncontradoException("Avistamiento no encontrado con ID: " + id);
		}
		repository.deleteById(id);
	}

	// Punto Bonus: Resumen agrupado por especie
	public List<ResumenEspecieDTO> obtenerResumenPorEspecie() {
		List<Object[]> resultados = repository.obtenerResumenPorEspecie();
		return resultados.stream().map(obj -> new ResumenEspecieDTO((String) obj[0], (Long) obj[1]))
				.collect(Collectors.toList());
	}

	// Métodos utilitarios para convertir entre Entidad y DTO
	private AvistamientoDTO convertirADTO(Avistamiento a) {
		AvistamientoDTO dto = new AvistamientoDTO();
		dto.setId(a.getId());
		dto.setEspecie(a.getEspecie());
		dto.setLugar(a.getLugar());
		dto.setFecha(a.getFecha());
		dto.setObservador(a.getObservador());
		return dto;
	}

	private Avistamiento convertirAEntidad(AvistamientoDTO dto) {
		return new Avistamiento(dto.getId(), dto.getEspecie(), dto.getLugar(), dto.getFecha(), dto.getObservador());
	}
}