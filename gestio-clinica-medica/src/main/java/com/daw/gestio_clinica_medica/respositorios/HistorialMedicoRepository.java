package com.daw.clinica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.clinica.models.HistorialMedico;

/**
 * Repositorio JPA para la entidad HistorialMedico.
 * Proporciona operaciones CRUD básicas sobre la tabla correspondiente.
 */
@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {

}
