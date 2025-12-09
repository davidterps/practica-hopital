package com.daw.gestio_clinica_medica.modelos;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

/**
 * Entidad que representa a un médico de la clínica.
 */
@Entity
@Table(name = "medicos")
public class Medico {

    /**
     * Clave primaria de la tabla medicos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedico;

    /**
     * Nombre del médico.
     */
    @Column(nullable = false, length = 50)
    private String nombre;

    /**
     * Apellidos del médico.
     */
    @Column(nullable = false, length = 100)
    private String apellidos;

    /**
     * Especialidad a la que pertenece este médico.
     * Relación muchos a uno: muchos médicos pueden tener la misma especialidad.
     */
    @ManyToOne
    @JoinColumn(name = "id_especialidad")
    private Especialidad especialidad;

    /**
     * Lista de citas asociadas a este médico.
     */
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas = new ArrayList<>();

    /**
     * Constructor vacío requerido por JPA.
     */
    public Medico() {
    }

    /**
     * Constructor con parámetros para crear un médico con sus datos básicos.
     *
     * @param nombre      nombre del médico
     * @param apellidos   apellidos del médico
     * @param especialidad especialidad del médico
     */
    public Medico(String nombre, String apellidos, Especialidad especialidad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    @Override
    public String toString() {
        return "Medico [idMedico=" + idMedico + ", nombre=" + nombre + ", apellidos=" + apellidos + "]";
    }
}
