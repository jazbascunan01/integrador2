package org.example.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.modelo.Carrera;
import org.example.modelo.Estudiante;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Estudiante_CarreraDTO {
    private int id_estudiante_carrera;
    private Estudiante estudiante;
    private Carrera carrera;
    private int anio_inscripcion;
    private int anio_graduacion;
    private int antiguedad;
}
