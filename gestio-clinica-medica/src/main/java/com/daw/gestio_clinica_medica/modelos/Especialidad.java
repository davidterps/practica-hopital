package com.daw.gestio_clinica_medica.modelos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * Entidad que representa una especialidad médica (Cardiología, Pediatría, etc.).
 */
@Entity
@Table(name = "especialidades")
public class Especialidad {

    /**
     * Clave primaria de la tabla especialidades.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspecialidad;

    /**
     * Nombre de la especialidad.
     */
    @Column(nullable = false, length = 100)
    private String nombreEspecialidad;

    /**
     * Lista de médicos asociados a esta especialidad.
     * Relación uno a muchos.
     */
    @OneToMany(mappedBy = "especialidad", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "especialidad-medico") // Evita recursión al no serializar las citas en el 
    private List<Medico> medicos = new ArrayList<>();

    /**
     * Constructor vacío requerido por JPA.
     */
    public Especialidad() {
    }

    /**
     * Constructor con parámetros para crear una especialidad.
     *
     * @param nombreEspecialidad nombre de la especialidad
     */
    public Especialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }

    @Override
    public String toString() {
        return "Especialidad [idEspecialidad=" + idEspecialidad + ", nombreEspecialidad=" + nombreEspecialidad + "]";
    }
}
