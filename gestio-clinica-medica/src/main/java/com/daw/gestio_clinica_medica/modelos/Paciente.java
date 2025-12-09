package com.daw.clinica.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

/**
 * Entidad que representa a un paciente de la clínica.
 * Incluye sus datos personales y las relaciones con citas e historial médico.
 */
@Entity // Indica que esta clase es una entidad JPA que se mapea a una tabla de la base de datos
@Table(name = "pacientes") // Especifica el nombre de la tabla asociada a esta entidad
public class Paciente {

    /**
     * Clave primaria de la tabla pacientes.
     * Se genera automáticamente con una estrategia de identidad.
     */
    @Id // Marca este atributo como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el valor se genera automáticamente en la BD
    private Long idPaciente;

    /**
     * Nombre del paciente.
     */
    @Column(nullable = false, length = 50) // Columna obligatoria con longitud máxima de 50 caracteres
    private String nombre;

    /**
     * Apellidos del paciente.
     */
    @Column(nullable = false, length = 100) // Columna obligatoria con longitud máxima de 100 caracteres
    private String apellidos;

    /**
     * Documento de identificación del paciente.
     */
    @Column(nullable = false, unique = true, length = 20) // Valor obligatorio y único
    private String dni;

    /**
     * Fecha de nacimiento del paciente.
     */
    private LocalDate fechaNacimiento;

    /**
     * Lista de citas asociadas a este paciente.
     * Relación uno a muchos: un paciente puede tener muchas citas.
     */
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas = new ArrayList<>();

    /**
     * Historial médico asociado a este paciente.
     * Relación uno a uno: un paciente tiene un único historial.
     */
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
    private HistorialMedico historial;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Paciente() {
    }

    /**
     * Constructor con parámetros para crear un paciente con sus datos básicos.
     *
     * @param nombre          nombre del paciente
     * @param apellidos       apellidos del paciente
     * @param dni             documento de identidad
     * @param fechaNacimiento fecha de nacimiento
     */
    public Paciente(String nombre, String apellidos, String dni, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Métodos getters y setters

    /**
     * Devuelve el identificador del paciente.
     *
     * @return id del paciente
     */
    public Long getIdPaciente() {
        return idPaciente;
    }

    /**
     * Establece el identificador del paciente.
     *
     * @param idPaciente nuevo id
     */
    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    /**
     * Devuelve el nombre del paciente.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del paciente.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve los apellidos del paciente.
     *
     * @return apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del paciente.
     *
     * @param apellidos nuevos apellidos
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Devuelve el DNI del paciente.
     *
     * @return dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del paciente.
     *
     * @param dni nuevo documento de identidad
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Devuelve la fecha de nacimiento del paciente.
     *
     * @return fecha de nacimiento
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del paciente.
     *
     * @param fechaNacimiento nueva fecha
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Devuelve la lista de citas del paciente.
     *
     * @return lista de citas
     */
    public List<Cita> getCitas() {
        return citas;
    }

    /**
     * Establece la lista de citas del paciente.
     *
     * @param citas nueva lista de citas
     */
    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    /**
     * Devuelve el historial médico del paciente.
     *
     * @return historial médico
     */
    public HistorialMedico getHistorial() {
        return historial;
    }

    /**
     * Establece el historial médico del paciente.
     *
     * @param historial nuevo historial
     */
    public void setHistorial(HistorialMedico historial) {
        this.historial = historial;
    }

    @Override
    public String toString() {
        return "Paciente [idPaciente=" + idPaciente + ", nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni
                + "]";
    }
}
