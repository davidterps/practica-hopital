package com.daw.clinica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.clinica.models.HistorialMedico;
import com.daw.clinica.repositories.HistorialMedicoRepository;

/**
 * Servicio que encapsula la lógica de negocio relacionada con la entidad HistorialMedico.
 * Utiliza el repositorio correspondiente para acceder a la base de datos.
 */
@Service
public class HistorialMedicoService {

    /**
     * Repositorio JPA para acceder a la entidad HistorialMedico en la base de datos.
     */
    @Autowired
    private HistorialMedicoRepository historialMedicoRepository;

    /**
     * Devuelve una lista con todas las entidades HistorialMedico almacenadas.
     *
     * @return lista de HistorialMedico
     */
    public List<HistorialMedico> findAll() {
        return historialMedicoRepository.findAll();
    }

    /**
     * Busca una entidad HistorialMedico por su identificador.
     *
     * @param id identificador de la entidad
     * @return un Optional que puede contener la entidad encontrada o estar vacío
     */
    public Optional<HistorialMedico> findById(Long id) {
        return historialMedicoRepository.findById(id);
    }

    /**
     * Guarda o actualiza una entidad HistorialMedico en la base de datos.
     *
     * @param historialMedico entidad a guardar
     * @return entidad guardada
     */
    public HistorialMedico save(HistorialMedico historialMedico) {
        return historialMedicoRepository.save(historialMedico);
    }

    /**
     * Elimina una entidad HistorialMedico por su identificador.
     *
     * @param id identificador de la entidad a eliminar
     */
    public void deleteById(Long id) {
        historialMedicoRepository.deleteById(id);
    }
}
