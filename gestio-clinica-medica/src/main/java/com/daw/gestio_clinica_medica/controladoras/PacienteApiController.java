package com.daw.gestio_clinica_medica.controladoras;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.daw.gestio_clinica_medica.modelos.Cita;
import com.daw.gestio_clinica_medica.modelos.HistorialMedico;
import com.daw.gestio_clinica_medica.modelos.Paciente;
import com.daw.gestio_clinica_medica.servicios.CitaService;
import com.daw.gestio_clinica_medica.servicios.HistorialMedicoService;
import com.daw.gestio_clinica_medica.servicios.PacienteService;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteApiController {

    // importación de servicios
    private final PacienteService pacienteService;
    private final CitaService citaService;
    private final HistorialMedicoService historialService;

    public PacienteApiController(PacienteService pacienteService, CitaService citaService,
                                 HistorialMedicoService historialService) {
        this.pacienteService = pacienteService;
        this.citaService = citaService;
        this.historialService = historialService;
    }

    // ================== CRUD PACIENTES ==================

    @GetMapping()
    public List<Paciente> listaPacientes() {
        return pacienteService.findAll();
    }

    @GetMapping("/{id}")
    public Paciente pacientePorId(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Paciente> paciente = pacienteService.findById(id);
        if (paciente.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorSF", "404 not found");
            return null;
        }
        redirectAttributes.addFlashAttribute("estadoSF", "200 OK");
        return paciente.get();
    }

    @PostMapping()
    public Paciente nuevoPaciente(@RequestBody Paciente data, RedirectAttributes redirectAttributes) {
        return pacienteService.save(data);
    }

    @PutMapping("/{id}")
    public Paciente modificarPaciente(@PathVariable Long id, @RequestBody Paciente data) {

        Optional<Paciente> paciente = pacienteService.findById(id);
        if (paciente.isEmpty()) {
            return null;
        }

        paciente.get().setNombre(data.getNombre());
        paciente.get().setApellidos(data.getApellidos());
        paciente.get().setDni(data.getDni());
        paciente.get().setFechaNacimiento(data.getFechaNacimiento());

        return pacienteService.save(paciente.get());
    }

    @DeleteMapping("/{id}")
    public String borrarPaciente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Paciente> paciente = pacienteService.findById(id);
        if (paciente.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorSF", "404 not found");
            return null;
        }

        pacienteService.deleteById(id);
        return "Paciente con id -> " + paciente.get().getIdPaciente() + " borrado con éxito";
    }

    // ================== CITAS DEL PACIENTE ==================

    @GetMapping("/{id}/citas")
    public List<Cita> citasPorPaciente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Paciente> paciente = pacienteService.findById(id);

        if (paciente.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorSF", "404 not found");
            return null;
        }

        redirectAttributes.addFlashAttribute("estadoSF", "200 OK");
        return paciente.get().getCitas();
    }

    @PostMapping("/{id}/citas")
    public Cita nuevaCitaParaPaciente(@PathVariable Long id,
                                      @RequestBody Cita cita,
                                      RedirectAttributes redirectAttributes) {

        Optional<Paciente> paciente = pacienteService.findById(id);
        if (paciente.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorSF", "404 not found");
            return null;
        }

        cita.setPaciente(paciente.get());
        return citaService.save(cita);
    }

    // ================== HISTORIAL MÉDICO ==================

    @GetMapping("/{id}/historial")
    public HistorialMedico historialPorPaciente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Paciente> paciente = pacienteService.findById(id);

        if (paciente.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorSF", "404 not found");
            return null;
        }

        return paciente.get().getHistorial();
    }

    @PostMapping("/{id}/historial")
    public HistorialMedico nuevoHistorial(@PathVariable Long id,
                                          @RequestBody HistorialMedico historial,
                                          RedirectAttributes redirectAttributes) {

        Optional<Paciente> paciente = pacienteService.findById(id);
        if (paciente.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorSF", "404 not found");
            return null;
        }

        historial.setPaciente(paciente.get());
        return historialService.save(historial);
    }

    @PutMapping("/{id}/historial")
    public HistorialMedico modificarHistorial(@PathVariable Long id,
                                              @RequestBody HistorialMedico data,
                                              RedirectAttributes redirectAttributes) {

        Optional<Paciente> paciente = pacienteService.findById(id);
        if (paciente.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorSF", "404 not found");
            return null;
        }

        HistorialMedico historialExistente = paciente.get().getHistorial();
        if (historialExistente == null) {
            redirectAttributes.addFlashAttribute("errorSF", "404 historial not found");
            return null;
        }

        data.setIdHistorial(historialExistente.getIdHistorial());
        data.setPaciente(paciente.get());

        return historialService.save(data);
    }
}
