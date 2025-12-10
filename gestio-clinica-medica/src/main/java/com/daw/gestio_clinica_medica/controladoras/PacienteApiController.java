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


	/**=========================================================
	 * IMPORTANTE:
	 * TODOS LOS @RETURN Y @PARAM NO SON NECESARIOS PARA LA EJECUCION DEL CODIGO
	 * ESTAS LINEAS SON NECESARIAS SOLO PARA LA DOCUMENTACION DEL JAVADOC
	 * =========================================================
	 */
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
	 * GET /api/pacientes/{idPaciente} : Devuelve un paciente por idPaciente.
	 *
	 * @param idPaciente identificador del paciente
	 * @return paciente si existe, null o mensaje si no
	 */
	@GetMapping("/{idPaciente}")
	public Paciente getPacienteById(@PathVariable Long idPaciente) {
		Optional<Paciente> paciente = pacienteService.findById(idPaciente);
		if (paciente.isPresent()) {
			System.out.println("[OK 200] Paciente encontrado: " + paciente.get().getIdPaciente());
			return paciente.get();
		} else {
			System.out.println("[ERROR 404] Paciente con idPaciente " + idPaciente + " no encontrado");
			return null;
		}
	}

	/**
	 * POST /api/pacientes : Crea un nuevo paciente.
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
			System.out.println("[OK 201] Paciente creado con idPaciente: " + pacienteGuardado.getIdPaciente());
			redirectAttributes.addFlashAttribute("success", "Paciente creado exitosamente");
			return pacienteGuardado;
		} catch (Exception e) {
			System.out.println("[ERROR 500] Error al guardar paciente: " + e.getMessage());
			redirectAttributes.addFlashAttribute("error", "Error al crear paciente");
			return null;
		}
	}

	/**
	 * PUT /api/pacientes/{idPaciente} : Modifica un paciente existente.
	 *
	 * @param idPaciente       identificador del paciente a actualizar
	 * @param paciente datos actualizados
	 * @param redirectAttributes para mensajes de validación
	 * @return paciente actualizado o null si no existe
	 */
	@PutMapping("/{idPaciente}")
	public Paciente updatePaciente(@PathVariable Long idPaciente, @RequestBody Paciente paciente,
			RedirectAttributes redirectAttributes) {
		Optional<Paciente> pacienteExistente = pacienteService.findById(idPaciente);
		if (pacienteExistente.isPresent()) {
			paciente.setIdPaciente(idPaciente);
			try {
				Paciente pacienteActualizado = pacienteService.save(paciente);
				System.out.println("[OK 200] Paciente actualizado: " + idPaciente);
				redirectAttributes.addFlashAttribute("success", "Paciente actualizado exitosamente");
				return pacienteActualizado;
			} catch (Exception e) {
				System.out.println("[ERROR 500] Error al actualizar paciente: " + e.getMessage());
				redirectAttributes.addFlashAttribute("error", "Error al actualizar paciente");
				return null;
			}
		} else {
			System.out.println("[ERROR 404] Paciente con idPaciente " + idPaciente + " no encontrado para actualizar");
			redirectAttributes.addFlashAttribute("error", "Paciente no encontrado");
			return null;
		}
	}

	/**
	 * DELETE /api/pacientes/{idPaciente} : Elimina un paciente.
	 *
	 * @param idPaciente identificador del paciente a eliminar
	 * @param redirectAttributes para mensajes de validación
	 * @return mensaje de confirmación o error
	 */
	@DeleteMapping("/{idPaciente}")
	public String deletePaciente(@PathVariable Long idPaciente, RedirectAttributes redirectAttributes) {
		Optional<Paciente> paciente = pacienteService.findById(idPaciente);
		if (paciente.isPresent()) {
			try {
				pacienteService.deleteById(idPaciente);
				System.out.println("[OK 204] Paciente eliminado: " + idPaciente);
				redirectAttributes.addFlashAttribute("success", "Paciente eliminado exitosamente");
				return "Paciente eliminado";
			} catch (Exception e) {
				System.out.println("[ERROR 500] Error al eliminar paciente: " + e.getMessage());
				redirectAttributes.addFlashAttribute("error", "Error al eliminar paciente");
				return "Error";
			}
		} else {
			System.out.println("[ERROR 404] Paciente con idPaciente " + idPaciente + " no encontrado para eliminar");
			redirectAttributes.addFlashAttribute("error", "Paciente no encontrado");
			return "No encontrado";
		}
	}

	// ========== ENDPOINTS CITAS ==========

	/**
	 * GET /api/pacientes/{idPaciente}/citas : Devuelve todas las citas del paciente.
	 *
	 * @param idPaciente identificador del paciente
	 * @return lista de citas o null si paciente no existe
	 */
	@GetMapping("/{idPaciente}/citas")
	public List<Cita> getCitasPaciente(@PathVariable Long idPaciente) {
		Optional<Paciente> paciente = pacienteService.findById(idPaciente);
		if (paciente.isPresent()) {
			System.out.println("[OK 200] Citas del paciente " + idPaciente + " obtenidas");
			return paciente.get().getCitas();
		} else {
			System.out.println("[ERROR 404] Paciente con idPaciente " + idPaciente + " no encontrado");
			return null;
		}
	}

	/**
	 * POST /api/pacientes/{idPaciente}/citas : Crea una nueva cita para ese paciente.
	 *
	 * @param idPaciente   identificador del paciente
	 * @param cita objeto cita a crear
	 * @param redirectAttributes para mensajes de validación
	 * @return cita creada o null si paciente no existe
	 */
	@PostMapping("/{idPaciente}/citas")
	public Cita createCitaPaciente(@PathVariable Long idPaciente, @RequestBody Cita cita,
			RedirectAttributes redirectAttributes) {
		Optional<Paciente> paciente = pacienteService.findById(idPaciente);
		if (paciente.isPresent()) {
			try {
				cita.setPaciente(paciente.get());
				Cita citaGuardada = citaService.save(cita);
				System.out.println("[OK 201] Cita creada para paciente: " + idPaciente);
				redirectAttributes.addFlashAttribute("success", "Cita creada exitosamente");
				return citaGuardada;
			} catch (Exception e) {
				System.out.println("[ERROR 500] Error al crear cita: " + e.getMessage());
				redirectAttributes.addFlashAttribute("error", "Error al crear cita");
				return null;
			}
		} else {
			System.out.println("[ERROR 404] Paciente con idPaciente " + idPaciente + " no encontrado");
			redirectAttributes.addFlashAttribute("error", "Paciente no encontrado");
			return null;
		}
	}

	// ========== ENDPOINTS HISTORIAL MÉDICO ==========

	/**
	 * GET /api/pacientes/{idPaciente}/historial : Devuelve el historial médico del paciente.
	 *
	 * @param idPaciente identificador del paciente
	 * @return historial médico o null
	 */
	@GetMapping("/{idPaciente}/historial")
	public HistorialMedico getHistorialPaciente(@PathVariable Long idPaciente) {
		Optional<Paciente> paciente = pacienteService.findById(idPaciente);
		if (paciente.isPresent()) {
			HistorialMedico historial = paciente.get().getHistorial();
			if (historial != null) {
				System.out.println("[OK 200] Historial del paciente " + idPaciente + " obtenido");
				return historial;
			} else {
				System.out.println("[ERROR 404] Historial no encontrado para paciente: " + idPaciente);
				return null;
			}
		} else {
			System.out.println("[ERROR 404] Paciente con idPaciente " + idPaciente + " no encontrado");
			return null;
		}
	}

	/**
	 * POST /api/pacientes/{idPaciente}/historial : Crea un historial médico para el paciente.
	 *
	 * @param idPaciente       identificador del paciente
	 * @param historial objeto historial a crear
	 * @param redirectAttributes para mensajes de validación
	 * @return historial creado o null si paciente no existe
	 */
	@PostMapping("/{idPaciente}/historial")
	public HistorialMedico createHistorialPaciente(@PathVariable Long idPaciente,
			@RequestBody HistorialMedico historial, RedirectAttributes redirectAttributes) {
		Optional<Paciente> paciente = pacienteService.findById(idPaciente);
		if (paciente.isPresent()) {
			try {
				historial.setPaciente(paciente.get());
				HistorialMedico historialGuardado = historialMedicoService.save(historial);
				System.out.println("[OK 201] Historial creado para paciente: " + idPaciente);
				redirectAttributes.addFlashAttribute("success", "Historial creado exitosamente");
				return historialGuardado;
			} catch (Exception e) {
				System.out.println("[ERROR 500] Error al crear historial: " + e.getMessage());
				redirectAttributes.addFlashAttribute("error", "Error al crear historial");
				return null;
			}
		} else {
			System.out.println("[ERROR 404] Paciente con idPaciente " + idPaciente + " no encontrado");
			redirectAttributes.addFlashAttribute("error", "Paciente no encontrado");
			return null;
		}
	}

	/**
	 * PUT /api/pacientes/{idPaciente}/historial : Actualiza el historial médico del paciente.
	 *
	 * @param idPaciente       identificador del paciente
	 * @param historial datos actualizados del historial
	 * @param redirectAttributes para mensajes de validación
	 * @return historial actualizado o null si no existe
	 */
	@PutMapping("/{idPaciente}/historial")
	public HistorialMedico updateHistorialPaciente(@PathVariable Long idPaciente,
			@RequestBody HistorialMedico historial, RedirectAttributes redirectAttributes) {
		Optional<Paciente> paciente = pacienteService.findById(idPaciente);
		if (paciente.isPresent()) {
			HistorialMedico historialExistente = paciente.get().getHistorial();
			if (historialExistente != null) {
				try {
					historial.setIdHistorial(historialExistente.getIdHistorial());
					historial.setPaciente(paciente.get());
					HistorialMedico historialActualizado = historialMedicoService.save(historial);
					System.out.println("[OK 200] Historial actualizado para paciente: " + idPaciente);
					redirectAttributes.addFlashAttribute("success", "Historial actualizado exitosamente");
					return historialActualizado;
				} catch (Exception e) {
					System.out.println("[ERROR 500] Error al actualizar historial: " + e.getMessage());
					redirectAttributes.addFlashAttribute("error", "Error al actualizar historial");
					return null;
				}
			} else {
				System.out.println("[ERROR 404] Historial no encontrado para paciente: " + idPaciente);
				redirectAttributes.addFlashAttribute("error", "Historial no encontrado");
				return null;
			}
		} else {
			System.out.println("[ERROR 404] Paciente con idPaciente " + idPaciente + " no encontrado");
			redirectAttributes.addFlashAttribute("error", "Paciente no encontrado");
			return null;
		}
	}
}
