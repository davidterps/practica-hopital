package com.daw.gestio_clinica_medica.controladoras;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.daw.gestio_clinica_medica.modelos.Cita;
import com.daw.gestio_clinica_medica.modelos.Especialidad;
import com.daw.gestio_clinica_medica.modelos.Medico;
import com.daw.gestio_clinica_medica.servicios.EspecialidadService;
import com.daw.gestio_clinica_medica.servicios.MedicoService;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/medicos")
public class MedicoApiController {

    // importacion de servicios
    private final MedicoService medicoService;
    private final EspecialidadService especialidadService;

    public MedicoApiController(MedicoService medicoService, EspecialidadService especialidadService) {
        this.medicoService = medicoService;
        this.especialidadService = especialidadService;
    }

    @GetMapping()
    public List<Medico> listaMedicos() {
        List<Medico> medicos = medicoService.findAll();
        return medicos;
    }

    @GetMapping("/{id}")
    public Medico medicoPorId(@PathVariable Long id, RedirectAttributes redirectAtrttibutes) {
        // manejar errores por no encontrar al medico en la base de datos
        Optional<Medico> medico = medicoService.findById(id);
        if (medico.isEmpty()) {
            redirectAtrttibutes.addFlashAttribute("errorSF",
                    "404 not found");
            return null;
        }
        redirectAtrttibutes.addFlashAttribute("estadoSF",
                "200 OK");
        return medico.get();
    }

    @PostMapping()
    public Medico nuevoMedico(@RequestBody Medico med, RedirectAttributes redirectAtrttibutes) {
        if (especialidadService.findById(med.getEspecialidad().getIdEspecialidad()).isEmpty()) {
            redirectAtrttibutes.addFlashAttribute("errorSF",
                    "404 not found");
            return null;
        }
        // guarda el nuevo medico
        return medicoService.save(med);

    }

    @PutMapping("/{id}")
    public Medico modificarMedico(@PathVariable Long id, @RequestBody Medico data) {
        // manejar errores por no encontrar al medico en la base de datos
        Optional<Medico> medico = medicoService.findById(id);
        if (medico.isEmpty()) {
            return null;
        }

        // modifico cada dato recibido en el medico por id
        medico.get().setNombre(data.getNombre());
        medico.get().setApellidos(data.getApellidos());
        medico.get().setCitas(data.getCitas());
        medico.get().setEspecialidad(data.getEspecialidad());

        return medicoService.save(medico.get());
    }

    @DeleteMapping("/{id}")
    public String borrarMedico(@PathVariable Long id, RedirectAttributes redirectAtrttibutes) {
        // manejar errores por no encontrar al medico en la base de datos
        Optional<Medico> medico = medicoService.findById(id);
        if (medico.isEmpty()) {
            redirectAtrttibutes.addFlashAttribute("errorSF",
                    "404 not found");
            return null;
        }
        // elimina la entidad y muestra un mensaje
        medicoService.deleteById(id);
        return "Medico con id ->" + medico.get().getIdMedico() + " borrado con exito";
    }

    @GetMapping("/{id}/citas")
    public List<Cita> citaMedicoPorId(@PathVariable Long id, RedirectAttributes redirectAtrttibutes) {
        // manejar errores por no encontrar al medico en la base de datos
        Optional<Medico> medico = medicoService.findById(id);
        if (medico.isEmpty()) {
            redirectAtrttibutes.addFlashAttribute("errorSF",
                    "404 not found");
            return null;
        }

        redirectAtrttibutes.addFlashAttribute("estadoSF",
                "200 OK");
        return medico.get().getCitas();
    }

    @GetMapping("/especialidad/{idEspecialidad}")
    public List<Medico> medicosPorEspecialidad(@PathVariable Long idEspecialidad,
            RedirectAttributes redirectAtrttibutes) {
        // manejar errores por si no se encuentra la especialidad en la base de datos
        Optional<Especialidad> especialidad = especialidadService.findById(idEspecialidad);
        if (especialidad.isEmpty()) {
            redirectAtrttibutes.addFlashAttribute("errorSF",
                    "404 not found");
            return null;
        }

        redirectAtrttibutes.addFlashAttribute("estadoSF",
                "200 OK");
        return especialidad.get().getMedicos();
    }

}
