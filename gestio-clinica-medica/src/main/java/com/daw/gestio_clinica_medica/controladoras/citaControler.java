package com.daw.gestio_clinica_medica.controladoras;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.gestio_clinica_medica.modelos.Cita;
import com.daw.gestio_clinica_medica.servicios.CitaService;

@RestController
@RequestMapping("/api")
public class citaControler {

private final CitaService citaService;

public citaControler(CitaService citaService) {
    this.citaService = citaService;
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
    return citaService.save(cita);
}


// PUT /api/citas/{id}
@PutMapping("/citas/{id}")
public Cita updateCita(@PathVariable Long id, @RequestBody Cita cita ) {
Cita c=citaService.findById(id).orElse(null);
c.setMedico(cita.getMedico());
c.setFechaHora(cita.getFechaHora());
c.setMotivo(cita.getMotivo());
c.setPaciente(cita.getPaciente());
return citaService.save(c);
}
// DELETE /api/citas/{id}
@DeleteMapping("/citas/{id}")
public void deleteCita(@PathVariable Long id) {
    citaService.deleteById(id);
}


// Consultas por relación:
// GET /api/citas/paciente/{idPaciente}
@GetMapping("/citas/paciente/{idPaciente}")
public List<Cita> getCitasByPaciente(@PathVariable Long idPaciente) {
   return citaService.findByPacienteId(idPaciente);
}


// GET /api/citas/medico/{idMedico}

@GetMapping("/citas/medico/{idMedico}")
public List<Cita> getCitasByMedico(@PathVariable Long idMedico) {
return citaService.findByMedicoId(idMedico);
}


}
