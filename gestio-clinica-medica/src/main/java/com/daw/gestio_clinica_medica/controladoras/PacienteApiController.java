package com.daw.gestio_clinica_medica.controladoras;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.gestio_clinica_medica.modelos.Paciente;
import com.daw.gestio_clinica_medica.servicios.PacienteService;
import org.springframework.web.bind.annotation.RequestParam;


/**
* Controladora REST para gestionar pacientes.
* Expone endpoints bajo la ruta /api/pacientes.
*/
@RestController // Indica que esta clase es una controladora REST
//que devuelve datos en formato JSON

@RequestMapping("/api/pacientes") // Ruta base para todos los
//endpoints de pacientes
public class PacienteApiController {
/**
* Servicio de pacientes que contiene la lógica de negocio
relacionada.
*/
private final PacienteService pacienteService;
/**
* Constructor que inyecta el servicio de pacientes.
*
* @param pacienteService servicio a inyectar
*/
public PacienteApiController(PacienteService pacienteService) {
this.pacienteService = pacienteService;
}
/**
* Devuelve la lista completa de pacientes.
*
* @return lista de pacientes en formato JSON
*/
    @GetMapping("pacientes")
    public List<Paciente> getAllPacientes() {
    return pacienteService.findAll();
    }
    @GetMapping("pacientesPorId")
    public String pacientesPorId(@RequestParam Long id) {
        Optional<Paciente> paciente = pacienteService.findById(id);
        if (paciente.isEmpty()) {
            
        }
            return pacienteService.findById(id).toString();
    }
    @PostMapping("/crearPaciente")
    public Paciente crearPaciente(@RequestBody Paciente paciente) {
        return this.pacienteService.save(paciente);
    }
    @GetMapping("/eliminar/{id}")
    public String eliminarPaciente(@PathVariable Long id) {
        this.pacienteService.deleteById(id);
        return "redirect:/pacientes";
    }
}
