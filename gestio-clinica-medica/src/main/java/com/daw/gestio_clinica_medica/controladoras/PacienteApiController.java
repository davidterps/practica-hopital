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

import com.daw.gestio_clinica_medica.modelos.Cita;
import com.daw.gestio_clinica_medica.modelos.HistorialMedico;
import com.daw.gestio_clinica_medica.modelos.Paciente;
import com.daw.gestio_clinica_medica.servicios.CitaService;
import com.daw.gestio_clinica_medica.servicios.HistorialMedicoService;
import com.daw.gestio_clinica_medica.servicios.PacienteService;

/**
 * Controladora REST para gestionar pacientes.
 * Expone endpoints bajo la ruta /api/pacientes.
 * Incluye operaciones CRUD y endpoints para relaciones (citas, historial).
 */
@RestController
@RequestMapping("/api/pacientes")
public class PacienteApiController {

	private final PacienteService pacienteService;
	private final CitaService citaService;
	private final HistorialMedicoService historialMedicoService;

	/**
	 * Constructor que inyecta los servicios necesarios.
	 */
	public PacienteApiController(PacienteService pacienteService, CitaService citaService,
			HistorialMedicoService historialMedicoService) {
		this.pacienteService = pacienteService;
		this.citaService = citaService;
		this.historialMedicoService = historialMedicoService;
	}

	// ========== ENDPOINTS CRUD PACIENTES ==========

	/**
	 * GET /api/pacientes → Lista todos los pacientes.
	 *
	 * @return lista de pacientes
	 */
	@GetMapping
	public List<Paciente> getAllPacientes() {
		return pacienteService.findAll();
	}

	/**
	 * GET /api/pacientes/{id} → Devuelve un paciente por id.
	 *
	 * @param id identificador del paciente
	 * @return paciente si existe, null o mensaje si no
	 */
	@GetMapping("/{id}")
	public Paciente getPacienteById(@PathVariable Long id) {
		Optional<Paciente> paciente = pacienteService.findById(id);
		if (paciente.isPresent()) {
			System.out.println("[OK 200] Paciente encontrado: " + paciente.get().getIdPaciente());
			return paciente.get();
		} else {
			System.out.println("[ERROR 404] Paciente con id " + id + " no encontrado");
			return null;
		}
	}

	/**
	 * POST /api/pacientes → Crea un nuevo paciente.
	 * Recibe JSON en el cuerpo.
	 *
	 * @param paciente objeto paciente a crear
	 * @param redirectAttributes para mensajes de validación
	 * @return paciente creado o null en caso de error
	 */
	@PostMapping
	public Paciente createPaciente(@RequestBody Paciente paciente, RedirectAttributes redirectAttributes) {
		if (paciente == null || paciente.getNombre() == null || paciente.getNombre().isEmpty()) {
			System.out.println("[ERROR 400] Datos inválidos para crear paciente");
			redirectAttributes.addFlashAttribute("error", "Error: Datos incompletos para crear paciente");
			return null;
		}
		try {
			Paciente pacienteGuardado = pacienteService.save(paciente);
			System.out.println("[OK 201] Paciente creado con id: " + pacienteGuardado.getIdPaciente());
			redirectAttributes.addFlashAttribute("success", "Paciente creado exitosamente");
			return pacienteGuardado;
		} catch (Exception e) {
			System.out.println("[ERROR 500] Error al guardar paciente: " + e.getMessage());
			redirectAttributes.addFlashAttribute("error", "Error al crear paciente");
			return null;
		}
	}

	/**
	 * PUT /api/pacientes/{id} → Modifica un paciente existente.
	 *
	 * @param id       identificador del paciente a actualizar
	 * @param paciente datos actualizados
	 * @param redirectAttributes para mensajes de validación
	 * @return paciente actualizado o null si no existe
	 */
	@PutMapping("/{id}")
	public Paciente updatePaciente(@PathVariable Long id, @RequestBody Paciente paciente,
			RedirectAttributes redirectAttributes) {
		Optional<Paciente> pacienteExistente = pacienteService.findById(id);
		if (pacienteExistente.isPresent()) {
			paciente.setIdPaciente(id);
			try {
				Paciente pacienteActualizado = pacienteService.save(paciente);
				System.out.println("[OK 200] Paciente actualizado: " + id);
				redirectAttributes.addFlashAttribute("success", "Paciente actualizado exitosamente");
				return pacienteActualizado;
			} catch (Exception e) {
				System.out.println("[ERROR 500] Error al actualizar paciente: " + e.getMessage());
				redirectAttributes.addFlashAttribute("error", "Error al actualizar paciente");
				return null;
			}
		} else {
			System.out.println("[ERROR 404] Paciente con id " + id + " no encontrado para actualizar");
			redirectAttributes.addFlashAttribute("error", "Paciente no encontrado");
			return null;
		}
	}

	/**
	 * DELETE /api/pacientes/{id} → Elimina un paciente.
	 *
	 * @param id identificador del paciente a eliminar
	 * @param redirectAttributes para mensajes de validación
	 * @return mensaje de confirmación o error
	 */
	@DeleteMapping("/{id}")
	public String deletePaciente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Optional<Paciente> paciente = pacienteService.findById(id);
		if (paciente.isPresent()) {
			try {
				pacienteService.deleteById(id);
				System.out.println("[OK 204] Paciente eliminado: " + id);
				redirectAttributes.addFlashAttribute("success", "Paciente eliminado exitosamente");
				return "Paciente eliminado";
			} catch (Exception e) {
				System.out.println("[ERROR 500] Error al eliminar paciente: " + e.getMessage());
				redirectAttributes.addFlashAttribute("error", "Error al eliminar paciente");
				return "Error";
			}
		} else {
			System.out.println("[ERROR 404] Paciente con id " + id + " no encontrado para eliminar");
			redirectAttributes.addFlashAttribute("error", "Paciente no encontrado");
			return "No encontrado";
		}
	}

	// ========== ENDPOINTS CITAS ==========

	/**
	 * GET /api/pacientes/{id}/citas → Devuelve todas las citas del paciente.
	 *
	 * @param id identificador del paciente
	 * @return lista de citas o null si paciente no existe
	 */
	@GetMapping("/{id}/citas")
	public List<Cita> getCitasPaciente(@PathVariable Long id) {
		Optional<Paciente> paciente = pacienteService.findById(id);
		if (paciente.isPresent()) {
			System.out.println("[OK 200] Citas del paciente " + id + " obtenidas");
			return paciente.get().getCitas();
		} else {
			System.out.println("[ERROR 404] Paciente con id " + id + " no encontrado");
			return null;
		}
	}

	/**
	 * POST /api/pacientes/{id}/citas → Crea una nueva cita para ese paciente.
	 *
	 * @param id   identificador del paciente
	 * @param cita objeto cita a crear
	 * @param redirectAttributes para mensajes de validación
	 * @return cita creada o null si paciente no existe
	 */
	@PostMapping("/{id}/citas")
	public Cita createCitaPaciente(@PathVariable Long id, @RequestBody Cita cita,
			RedirectAttributes redirectAttributes) {
		Optional<Paciente> paciente = pacienteService.findById(id);
		if (paciente.isPresent()) {
			try {
				cita.setPaciente(paciente.get());
				Cita citaGuardada = citaService.save(cita);
				System.out.println("[OK 201] Cita creada para paciente: " + id);
				redirectAttributes.addFlashAttribute("success", "Cita creada exitosamente");
				return citaGuardada;
			} catch (Exception e) {
				System.out.println("[ERROR 500] Error al crear cita: " + e.getMessage());
				redirectAttributes.addFlashAttribute("error", "Error al crear cita");
				return null;
			}
		} else {
			System.out.println("[ERROR 404] Paciente con id " + id + " no encontrado");
			redirectAttributes.addFlashAttribute("error", "Paciente no encontrado");
			return null;
		}
	}

	// ========== ENDPOINTS HISTORIAL MÉDICO ==========

	/**
	 * GET /api/pacientes/{id}/historial → Devuelve el historial médico del paciente.
	 *
	 * @param id identificador del paciente
	 * @return historial médico o null
	 */
	@GetMapping("/{id}/historial")
	public HistorialMedico getHistorialPaciente(@PathVariable Long id) {
		Optional<Paciente> paciente = pacienteService.findById(id);
		if (paciente.isPresent()) {
			HistorialMedico historial = paciente.get().getHistorial();
			if (historial != null) {
				System.out.println("[OK 200] Historial del paciente " + id + " obtenido");
				return historial;
			} else {
				System.out.println("[ERROR 404] Historial no encontrado para paciente: " + id);
				return null;
			}
		} else {
			System.out.println("[ERROR 404] Paciente con id " + id + " no encontrado");
			return null;
		}
	}

	/**
	 * POST /api/pacientes/{id}/historial → Crea un historial médico para el paciente.
	 *
	 * @param id       identificador del paciente
	 * @param historial objeto historial a crear
	 * @param redirectAttributes para mensajes de validación
	 * @return historial creado o null si paciente no existe
	 */
	@PostMapping("/{id}/historial")
	public HistorialMedico createHistorialPaciente(@PathVariable Long id,
			@RequestBody HistorialMedico historial, RedirectAttributes redirectAttributes) {
		Optional<Paciente> paciente = pacienteService.findById(id);
		if (paciente.isPresent()) {
			try {
				historial.setPaciente(paciente.get());
				HistorialMedico historialGuardado = historialMedicoService.save(historial);
				System.out.println("[OK 201] Historial creado para paciente: " + id);
				redirectAttributes.addFlashAttribute("success", "Historial creado exitosamente");
				return historialGuardado;
			} catch (Exception e) {
				System.out.println("[ERROR 500] Error al crear historial: " + e.getMessage());
				redirectAttributes.addFlashAttribute("error", "Error al crear historial");
				return null;
			}
		} else {
			System.out.println("[ERROR 404] Paciente con id " + id + " no encontrado");
			redirectAttributes.addFlashAttribute("error", "Paciente no encontrado");
			return null;
		}
	}

	/**
	 * PUT /api/pacientes/{id}/historial → Actualiza el historial médico del paciente.
	 *
	 * @param id       identificador del paciente
	 * @param historial datos actualizados del historial
	 * @param redirectAttributes para mensajes de validación
	 * @return historial actualizado o null si no existe
	 */
	@PutMapping("/{id}/historial")
	public HistorialMedico updateHistorialPaciente(@PathVariable Long id,
			@RequestBody HistorialMedico historial, RedirectAttributes redirectAttributes) {
		Optional<Paciente> paciente = pacienteService.findById(id);
		if (paciente.isPresent()) {
			HistorialMedico historialExistente = paciente.get().getHistorial();
			if (historialExistente != null) {
				try {
					historial.setIdHistorial(historialExistente.getIdHistorial());
					historial.setPaciente(paciente.get());
					HistorialMedico historialActualizado = historialMedicoService.save(historial);
					System.out.println("[OK 200] Historial actualizado para paciente: " + id);
					redirectAttributes.addFlashAttribute("success", "Historial actualizado exitosamente");
					return historialActualizado;
				} catch (Exception e) {
					System.out.println("[ERROR 500] Error al actualizar historial: " + e.getMessage());
					redirectAttributes.addFlashAttribute("error", "Error al actualizar historial");
					return null;
				}
			} else {
				System.out.println("[ERROR 404] Historial no encontrado para paciente: " + id);
				redirectAttributes.addFlashAttribute("error", "Historial no encontrado");
				return null;
			}
		} else {
			System.out.println("[ERROR 404] Paciente con id " + id + " no encontrado");
			redirectAttributes.addFlashAttribute("error", "Paciente no encontrado");
			return null;
		}
	}
}
