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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.daw.gestio_clinica_medica.modelos.HistorialMedico;
import com.daw.gestio_clinica_medica.servicios.HistorialMedicoService;

@RestController
@RequestMapping("/api/historiales")
public class HistorialApiController {
    // importacion de servicios
    private final HistorialMedicoService historialMedicoService;

    public HistorialApiController(HistorialMedicoService historialMedicoService) {
        this.historialMedicoService = historialMedicoService;
    }

    @GetMapping("")
    public List<HistorialMedico> listaMedicos() {
        List<HistorialMedico> historiales = historialMedicoService.findAll();
        return historiales;
    }

    @GetMapping("/{id}")
    public HistorialMedico medicoPorId(@PathVariable Long id,  RedirectAttributes redirectAtrttibutes) {
        // manejar errores por no encontrar al historial en la base de datos
        Optional<HistorialMedico> historial = historialMedicoService.findById(id);
        if (historial.isEmpty()) {
            redirectAtrttibutes.addFlashAttribute("errorSF",
            "404 not found");
            return null;
        }

        redirectAtrttibutes.addFlashAttribute("estadoSF",
                "200 OK");
        return historial.get();
    }

    @PostMapping("")
    public HistorialMedico nuevoMedico(@RequestBody HistorialMedico med) {
        // guarda el nuevo historial
        return historialMedicoService.save(med);
    }

    @PutMapping("/{id}")
    public HistorialMedico modificarMedico(@PathVariable Long id, @RequestBody HistorialMedico data,
            RedirectAttributes redirectAtrttibutes) {
        // manejar errores por no encontrar al historial en la base de datos
        Optional<HistorialMedico> historial = historialMedicoService.findById(id);
        if (historial.isEmpty()) {
            redirectAtrttibutes.addFlashAttribute("errorSF",
                    "404 not found");
            return null;
        }

        // modifico cada dato recibido en el historial por id
        historial.get().setInformacionClinica(data.getInformacionClinica());
        historial.get().setPaciente(data.getPaciente());

        redirectAtrttibutes.addFlashAttribute("estadoSF",
                "200 OK");
        return historialMedicoService.save(historial.get());
    }

    @DeleteMapping("/{id}")
    public String borrarMedico(@PathVariable Long id, RedirectAttributes redirectAtrttibutes) {
        // manejar errores por no encontrar al historial en la base de datos
        Optional<HistorialMedico> historial = historialMedicoService.findById(id);
        if (historial.isEmpty()) {
            redirectAtrttibutes.addFlashAttribute("errorSF",
                    "404 not found");
            return null;
        }
        // elimina la entidad y muestra un mensaje
        historialMedicoService.deleteById(id);

        return "HistorialMedico con id ->" + historial.get().getIdHistorial() + " borrado con exito";
    }
}
