package com.daw.gestio_clinica_medica.respositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.gestio_clinica_medica.modelos.HistorialMedico;



/**
 * Repositorio JPA para la entidad HistorialMedico.
 * Proporciona operaciones CRUD básicas sobre la tabla correspondiente.
 */
@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {

}
