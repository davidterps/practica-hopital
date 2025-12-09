package com.daw.clinica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.clinica.models.Especialidad;
import com.daw.clinica.repositories.EspecialidadRepository;

/**
 * Servicio que encapsula la lógica de negocio relacionada con la entidad Especialidad.
 * Utiliza el repositorio correspondiente para acceder a la base de datos.
 */
@Service
public class EspecialidadService {

    /**
     * Repositorio JPA para acceder a la entidad Especialidad en la base de datos.
     */
    @Autowired
    private EspecialidadRepository especialidadRepository;

    /**
     * Devuelve una lista con todas las entidades Especialidad almacenadas.
     *
     * @return lista de Especialidad
     */
    public List<Especialidad> findAll() {
        return especialidadRepository.findAll();
    }

    /**
     * Busca una entidad Especialidad por su identificador.
     *
     * @param id identificador de la entidad
     * @return un Optional que puede contener la entidad encontrada o estar vacío
     */
    public Optional<Especialidad> findById(Long id) {
        return especialidadRepository.findById(id);
    }

    /**
     * Guarda o actualiza una entidad Especialidad en la base de datos.
     *
     * @param especialidad entidad a guardar
     * @return entidad guardada
     */
    public Especialidad save(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    /**
     * Elimina una entidad Especialidad por su identificador.
     *
     * @param id identificador de la entidad a eliminar
     */
    public void deleteById(Long id) {
        especialidadRepository.deleteById(id);
    }
}
