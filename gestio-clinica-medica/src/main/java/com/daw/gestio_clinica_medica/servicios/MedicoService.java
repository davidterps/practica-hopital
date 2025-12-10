package com.daw.gestio_clinica_medica.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.daw.gestio_clinica_medica.modelos.Medico;
import com.daw.gestio_clinica_medica.respositorios.MedicoRepository;



/**
 * Servicio que encapsula la lógica de negocio relacionada con la entidad Medico.
 * Utiliza el repositorio correspondiente para acceder a la base de datos.
 */
@Service
public class MedicoService {

    /**
     * Repositorio JPA para acceder a la entidad Medico en la base de datos.
     */
    
    private final MedicoRepository medicoRepository;
    
    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    /**
     * Devuelve una lista con todas las entidades Medico almacenadas.
     *
     * @return lista de Medico
     */
    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }

    /**
     * Busca una entidad Medico por su identificador.
     *
     * @param id identificador de la entidad
     * @return un Optional que puede contener la entidad encontrada o estar vacío
     */
    public Optional<Medico> findById(Long id) {
        return medicoRepository.findById(id);
    }

    /**
     * Guarda o actualiza una entidad Medico en la base de datos.
     *
     * @param medico entidad a guardar
     * @return entidad guardada
     */
    public Medico save(Medico medico) {
        return medicoRepository.save(medico);
    }

    /**
     * Elimina una entidad Medico por su identificador.
     *
     * @param id identificador de la entidad a eliminar
     */
    public void deleteById(Long id) {
        medicoRepository.deleteById(id);
    }
}
