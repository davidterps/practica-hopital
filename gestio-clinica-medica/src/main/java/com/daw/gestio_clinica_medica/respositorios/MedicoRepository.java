package com.daw.clinica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.clinica.models.Medico;

/**
 * Repositorio JPA para la entidad Medico.
 * Proporciona operaciones CRUD básicas sobre la tabla correspondiente.
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

}
