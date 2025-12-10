package com.daw.gestio_clinica_medica.controladoras;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.daw.gestio_clinica_medica.modelos.Cita;
import com.daw.gestio_clinica_medica.modelos.Medico;
import com.daw.gestio_clinica_medica.modelos.Paciente;
import com.daw.gestio_clinica_medica.servicios.CitaService;
import com.daw.gestio_clinica_medica.servicios.MedicoService;
import com.daw.gestio_clinica_medica.servicios.PacienteService;



@RestController
@RequestMapping("/api")
public class citaControler {

    private final CitaService citaService;
    private final MedicoService medicoService;
    private final PacienteService pacienteService;

    public citaControler(CitaService citaService, MedicoService medicoService, PacienteService pacienteService) {
        this.citaService = citaService;
        this.medicoService = medicoService;
        this.pacienteService = pacienteService;
    }

    // GET /api/citas

    @GetMapping("/citas")
    public List<Cita> getAllCitas() {
        return citaService.findAll();
    }

    // GET /api/citas/{id}
    @GetMapping("/citas/{id}")
    public Cita getCitaById(Long id) {
        return citaService.findById(id).orElse(null);
    }

    // POST /api/citas
    @PostMapping("/citas")
    public Cita createCita(@RequestBody Cita cita) {
        //guarda una cita
        return citaService.save(cita);
    }

   

    // PUT /api/citas/{id}
    @PutMapping("/citas/{id}")
    public Cita updateCita(@PathVariable Long id, @RequestBody Cita cita) {
        Cita c = citaService.findById(id).orElse(null);
        c.setMedico(cita.getMedico());
        c.setFechaHora(cita.getFechaHora());
        c.setMotivo(cita.getMotivo());
        c.setPaciente(cita.getPaciente());
        return citaService.save(c);
    }

    // DELETE /api/citas/{id}
    @DeleteMapping("/citas/{id}")
    public void deleteCita(@PathVariable Long id) {
        //control de errores
        Cita cita = citaService.findById(id).orElse(null);
        if (cita == null) {
            deleteCita(id);
        }
    }

    // Consultas por relación:
    // GET /api/citas/paciente/{idPaciente}
    @GetMapping("/citas/paciente/{idPaciente}")
    public List<Cita> getCitasByPaciente(@PathVariable Long idPaciente) {
    Optional<Paciente> citas = pacienteService.findById(idPaciente);
    if (citas.isEmpty()) {
      return null;
    } else {
     return pacienteService.findById(idPaciente).get().getCitas();
    }


}

    // GET /api/citas/medico/{idMedico}

@GetMapping("/citas/medico/{idMedico}")
public List<Cita> getCitasByMedico(@PathVariable Long idMedico ) {
    Optional<Medico> citas = medicoService.findById(idMedico);
    if (citas.isEmpty()) {
      return null;
    } else {
     return medicoService.findById(idMedico).get().getCitas();
    }
}
}