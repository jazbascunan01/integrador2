package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Estudiante_CarreraDTO {
    private int id;
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String ciudadEstudiante;
    private String nombreCarrera;
    private int inscripcion;
    private int graduacion;
    private int antiguedad;

    @Override
    public String toString() {
        return String.format(
                "║ %-13d │ %-12s │ %-12s │ %-12s │ %-20s │ %-16d │ %-15d │ %-10d ║",
                id,
                cortar(nombreEstudiante, 12),
                cortar(apellidoEstudiante, 12),
                cortar(ciudadEstudiante, 12),
                cortar(nombreCarrera, 20),
                inscripcion,
                graduacion,
                antiguedad
        );
    }

    // Método auxiliar para cortar cadenas largas
    private String cortar(String texto, int maxLength) {
        if (texto == null) return "";
        if (texto.length() <= maxLength) {
            return texto;
        }
        return texto.substring(0, maxLength - 1) + "…"; // agrega '…' al final
    }
}
