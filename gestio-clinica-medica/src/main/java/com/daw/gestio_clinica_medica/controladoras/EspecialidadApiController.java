package com.daw.gestio_clinica_medica.controladoras;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.gestio_clinica_medica.modelos.Especialidad;
import com.daw.gestio_clinica_medica.modelos.Medico;
import com.daw.gestio_clinica_medica.servicios.EspecialidadService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadApiController {

    private final EspecialidadService especialidadService;
    
    public EspecialidadApiController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }   

    // metodo para devolver todas las especialidades
    @GetMapping("")
    public List<Especialidad> listarTodas(){

        return especialidadService.findAll();
    }

    // metodo para buscar una especialidad mediante un id
    @GetMapping("/{id}")
    public Especialidad buscarPorId(@PathVariable Long id){
        Optional<Especialidad> especialidad = this.especialidadService.findById(id);
        
        if(especialidad.isEmpty()){
            return null;
        }
        return especialidad.get();
    }

    // metodo para guardar o actualizar una especialidad
    @PutMapping("/{id}")
    public Especialidad guardarEspecialidad(@PathVariable Long id, @RequestBody Especialidad datos) {
        Especialidad e = especialidadService.findById(id).orElse(null);
        e.setMedicos(datos.getMedicos());
        e.setNombreEspecialidad(datos.getNombreEspecialidad());
        return especialidadService.save(e);
    }

    // metodo para borrar una especialidad mediante su id
    @DeleteMapping("/{id}")
    public void borrarEspecialidad(@PathVariable Long id) {
        especialidadService.deleteById(id);
    }

    @GetMapping("/{id}/medicos")
    public List<Medico> listarMedicosPorEspecialidad(@PathVariable Long id) {
        Optional<Especialidad> especialidad = this.especialidadService.findById(id);

        if(especialidad.isEmpty()){
            return null;
        }
        return especialidad.get().getMedicos();
    }
}
