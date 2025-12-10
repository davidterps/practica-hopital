package com.daw.gestio_clinica_medica.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

/**
 * Entidad que representa el historial médico de un paciente.
 */
@Entity
@Table(name = "historiales_medicos")
public class HistorialMedico {

    /**
     * Clave primaria de la tabla historiales_medicos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorial;

    /**
     * Información clínica relevante del paciente.
     */
    @Column(length = 2000)
    private String informacionClinica;

    /**
     * Paciente asociado a este historial.
     * Relación uno a uno.
     */
    @OneToOne
    @JoinColumn(name = "id_paciente", unique = true)
    @JsonIgnore  // Evita recursión al no serializar las citas en el 
    private Paciente paciente;

    /**
     * Constructor vacío requerido por JPA.
     */
    public HistorialMedico() {
    }

    /**
     * Constructor con parámetros para crear un historial médico.
     *
     * @param informacionClinica texto con la información clínica
     * @param paciente           paciente asociado
     */
    public HistorialMedico(String informacionClinica, Paciente paciente) {
        this.informacionClinica = informacionClinica;
        this.paciente = paciente;
    }

    public Long getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Long idHistorial) {
        this.idHistorial = idHistorial;
    }

    public String getInformacionClinica() {
        return informacionClinica;
    }

    public void setInformacionClinica(String informacionClinica) {
        this.informacionClinica = informacionClinica;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "HistorialMedico [idHistorial=" + idHistorial + "]";
    }
}
