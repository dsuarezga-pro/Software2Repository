package com.aves.api.repository;

import com.aves.api.model.Avistamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvistamientoRepository extends JpaRepository<Avistamiento, Long> {

	// Consulta personalizada para el punto bonus de resumen agrupado por especie
	@Query("SELECT a.especie AS especie, COUNT(a) AS total FROM Avistamiento a GROUP BY a.especie")
	List<Object[]> obtenerResumenPorEspecie();
}