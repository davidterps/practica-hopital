package com.daw.gestio_clinica_medica.controladoras;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.daw.gestio_clinica_medica.modelos.Especialidad;
import com.daw.gestio_clinica_medica.modelos.Medico;
import com.daw.gestio_clinica_medica.servicios.EspecialidadService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController // @RestController para convertir la controladora en una API Rest para trabajar con JSON en vez de usar vistas
@RequestMapping("/api/especialidades")  // @RequestMapping para designar la ruta por defecto de la controladora
public class EspecialidadApiController {

    private final EspecialidadService especialidadService;
    
    public EspecialidadApiController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }   

    // metodo para devolver todas las especialidades
    @GetMapping("") // @GetMapping para devolver información al usuario
    public List<Especialidad> listarTodas(){

        return especialidadService.findAll();
    }

    // metodo para buscar una especialidad mediante un id
    @GetMapping("/{id}")    // @GetMapping para devolver información al usuario
    public Especialidad buscarPorId(@PathVariable Long id, RedirectAttributes redirectAttributes){

        // comprobamos que la especialidad con ese id existe, si no existe manejamos el error con un mensaje y escapando del metodo
        Optional<Especialidad> especialidad = this.especialidadService.findById(id);
        
        if(especialidad.isEmpty()){
            redirectAttributes.addFlashAttribute("errorSF",
            "404: No se ha encontrado una especialidad con ese id");
            return null;
        }
        // devolvemos la especialidad con el id indicado
        return especialidad.get();
    }

    // metodo para guardar una especialidad
    @PostMapping    // PostMapping para guardar o actualizar información de la base de datos
    public Especialidad guardarEspecialidad(@RequestBody Especialidad datos) {
        return especialidadService.save(datos);
    }

    // metodo para actualizar una especialidad por su id
    @PutMapping("/{id}")    // PutMapping para actualizar información de la base de datos
    public Especialidad actualizarEspecialidad(@PathVariable Long id, @RequestBody Especialidad datos, RedirectAttributes redirectAttributes) {
        // comprobamos que la especialidad con ese id existe, si no existe manejamos el error con un mensaje y escapando del metodo
        Optional<Especialidad> especialidad = this.especialidadService.findById(id);
        
        if(especialidad.isEmpty()){
            redirectAttributes.addFlashAttribute("errorSF",
            "404: No se ha encontrado una especialidad con ese id");
            return null;
        }

        // actualizamos la información nueva en la base de datos
        especialidad.get().setMedicos(datos.getMedicos());
        especialidad.get().setNombreEspecialidad(datos.getNombreEspecialidad());
        return especialidadService.save(especialidad.get());
    }

    // metodo para borrar una especialidad mediante su id
    @DeleteMapping("/{id}") // @DeleteMapping para borrar información de la base de datos
    public void borrarEspecialidad(@PathVariable Long id,  RedirectAttributes redirectAttributes) {

        // comprobamos que la especialidad con ese id existe, si no existe manejamos el error con un mensaje y escapando del metodo
        Optional<Especialidad> especialidad = this.especialidadService.findById(id);
        
        if(especialidad.isEmpty()){
            redirectAttributes.addFlashAttribute("errorSF",
            "404: No se ha encontrado una especialidad con ese id");
        }
        
        // borramos la especialidad con el id indicado
        especialidadService.deleteById(id);
    }

    @GetMapping("/{id}/medicos") // @GetMapping para devolver información al usuario
    public List<Medico> listarMedicosPorEspecialidad(@PathVariable Long id,  RedirectAttributes redirectAttributes) {

        // comprobamos que la especialidad con ese id existe, si no existe manejamos el error con un mensaje y escapando del metodo
        Optional<Especialidad> especialidad = this.especialidadService.findById(id);
        
        if(especialidad.isEmpty()){
            redirectAttributes.addFlashAttribute("errorSF",
            "404: No se ha encontrado una especialidad con ese id");
            return null;
        }

        // devolvemos los medicos con la especialidad indicada
        return especialidad.get().getMedicos();
    }
}
