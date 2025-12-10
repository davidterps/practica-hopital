package com.daw.gestio_clinica_medica.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.daw.gestio_clinica_medica.modelos.Paciente;
import com.daw.gestio_clinica_medica.respositorios.PacienteRepository;



/**
 * Servicio que encapsula la lógica de negocio relacionada con la entidad Paciente.
 * Utiliza el repositorio correspondiente para acceder a la base de datos.
 */
@Service
public class PacienteService {

    /**
     * Repositorio JPA para acceder a la entidad Paciente en la base de datos.
     */
    
    private final PacienteRepository pacienteRepository;   
    
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }
    /**
     * Devuelve una lista con todas las entidades Paciente almacenadas.
     *
     * @return lista de Paciente
     */
    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    /**
     * Busca una entidad Paciente por su identificador.
     *
     * @param id identificador de la entidad
     * @return un Optional que puede contener la entidad encontrada o estar vacío
     */
    public Optional<Paciente> findById(Long id) {
        return pacienteRepository.findById(id);
    }

    /**
     * Guarda o actualiza una entidad Paciente en la base de datos.
     *
     * @param paciente entidad a guardar
     * @return entidad guardada
     */
    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    /**
     * Elimina una entidad Paciente por su identificador.
     *
     * @param id identificador de la entidad a eliminar
     */
    public void deleteById(Long id) {
        pacienteRepository.deleteById(id);
    }
}
