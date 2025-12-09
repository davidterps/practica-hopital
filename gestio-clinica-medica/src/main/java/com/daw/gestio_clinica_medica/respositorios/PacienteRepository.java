package com.daw.gestio_clinica_medica.respositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.gestio_clinica_medica.modelos.Paciente;


/**
 * Repositorio JPA para la entidad Paciente.
 * Proporciona operaciones CRUD básicas sobre la tabla correspondiente.
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}
