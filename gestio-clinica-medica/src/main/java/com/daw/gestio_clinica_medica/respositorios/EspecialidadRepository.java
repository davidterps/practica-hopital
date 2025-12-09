package com.daw.clinica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.clinica.models.Especialidad;

/**
 * Repositorio JPA para la entidad Especialidad.
 * Proporciona operaciones CRUD básicas sobre la tabla correspondiente.
 */
@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {

}
