---------------------------------------------------------
-- ESPECIALIDADES (se insertan primero)
---------------------------------------------------------
INSERT INTO especialidades (id_especialidad, nombre_especialidad)
VALUES 
(1, 'Cardiología'),
(2, 'Dermatología'),
(3, 'Pediatría'),
(4, 'Medicina General');

---------------------------------------------------------
-- MÉDICOS
---------------------------------------------------------
INSERT INTO medicos (id_medico, nombre, apellidos, id_especialidad)
VALUES
(1, 'Laura', 'Martínez López', 1),     -- Cardióloga
(2, 'Carlos', 'García Torres', 2),     -- Dermatólogo
(3, 'Ana', 'Serrano Ruiz', 3),         -- Pediatra
(4, 'Javier', 'Moreno Díaz', 4);       -- Médico general

---------------------------------------------------------
-- PACIENTES
---------------------------------------------------------
INSERT INTO pacientes (id_paciente, nombre, apellidos, dni, fecha_nacimiento)
VALUES
(1, 'Juan', 'Pérez Gómez', '12345678A', '1985-02-10'),
(2, 'María', 'López Sánchez', '87654321B', '1990-07-22'),
(3, 'Pedro', 'García Martínez', '11223344C', '1978-11-05');

---------------------------------------------------------
-- HISTORIALES MÉDICOS
---------------------------------------------------------
INSERT INTO historiales_medicos (id_historial, informacion_clinica, id_paciente)
VALUES
(1, 'Paciente con hipertensión controlada.', 1),
(2, 'Alergia a penicilina.', 2),
(3, 'Sin antecedentes médicos relevantes.', 3);

---------------------------------------------------------
-- CITAS
---------------------------------------------------------
INSERT INTO citas (id_cita, fecha_hora, motivo, id_paciente, id_medico)
VALUES
(1, '2025-01-15T10:30:00', 'Revisión anual', 1, 1),
(2, '2025-01-20T09:00:00', 'Dermatitis en brazo', 2, 2),
(3, '2025-02-05T12:00:00', 'Revisión infantil', 3, 3),
(4, '2025-02-10T08:30:00', 'Dolor abdominal', 1, 4),
(5, '2025-02-15T11:15:00', 'Consulta rutina', 2, 4);
