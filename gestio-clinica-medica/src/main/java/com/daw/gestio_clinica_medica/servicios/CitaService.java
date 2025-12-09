package com.daw.gestio_clinica_medica.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.gestio_clinica_medica.modelos.Cita;
import com.daw.gestio_clinica_medica.respositorios.CitaRepository;


/**
 * Servicio que encapsula la lógica de negocio relacionada con la entidad Cita.
 * Utiliza el repositorio correspondiente para acceder a la base de datos.
 */
@Service
public class CitaService {

    /**
     * Repositorio JPA para acceder a la entidad Cita en la base de datos.
     */
    @Autowired
    private CitaRepository citaRepository;

    /**
     * Devuelve una lista con todas las entidades Cita almacenadas.
     *
     * @return lista de Cita
     */
    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    /**
     * Busca una entidad Cita por su identificador.
     *
     * @param id identificador de la entidad
     * @return un Optional que puede contener la entidad encontrada o estar vacío
     */
    public Optional<Cita> findById(Long id) {
        return citaRepository.findById(id);
    }

    /**
     * Guarda o actualiza una entidad Cita en la base de datos.
     *
     * @param cita entidad a guardar
     * @return entidad guardada
     */
    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }

    /**
     * Elimina una entidad Cita por su identificador.
     *
     * @param id identificador de la entidad a eliminar
     */
    public void deleteById(Long id) {
        citaRepository.deleteById(id);
    }
    public List<Cita> findByPacienteId(Long idPaciente) {
        return citaRepository.findByPacienteId(idPaciente);
    }
    public List<Cita> findByMedicoId(Long idMedico) {
        return citaRepository.findByMedicoId(idMedico);

    }

}
