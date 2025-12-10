package com.daw.gestio_clinica_medica.modelos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

/**
 * Entidad que representa una cita médica.
 */
@Entity
@Table(name = "citas")
public class Cita {

    /**
     * Clave primaria de la tabla citas.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;

    /**
     * Fecha y hora de la cita.
     */
    private LocalDateTime fechaHora;

    /**
     * Motivo de la cita.
     */
    @Column(length = 255)
    private String motivo;

    /**
     * Paciente al que pertenece esta cita.
     * Relación muchos a uno.
     */
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    @JsonIgnore  // Evita recursión al no serializar las citas en el 
    private Paciente paciente;

    /**
     * Médico que atiende esta cita.
     * Relación muchos a uno.
     */
    @ManyToOne
    @JoinColumn(name = "id_medico")
    @JsonIgnore  // Evita recursión al no serializar las citas en el 
    private Medico medico;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Cita() {
    }

    /**
     * Constructor con parámetros para crear una cita.
     *
     * @param fechaHora fecha y hora de la cita
     * @param motivo    motivo de la cita
     * @param paciente  paciente asociado
     * @param medico    médico asociado
     */
    public Cita(LocalDateTime fechaHora, String motivo, Paciente paciente, Medico medico) {
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.paciente = paciente;
        this.medico = medico;
    }

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    @Override
    public String toString() {
        return "Cita [idCita=" + idCita + ", fechaHora=" + fechaHora + ", motivo=" + motivo + "]";
    }
}
