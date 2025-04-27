package org.example.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReporteCarreraDTO {
    private String carrera;
    private int anio;
    private long inscriptos;
    private long egresados;

    public ReporteCarreraDTO(String carrera, int anio) {
        this.carrera = carrera;
        this.anio = anio;
        this.inscriptos = 0;
        this.egresados = 0;
    }
    @Override
    public String toString() {
        return String.format("║ %-34s │ %-5d │ %-10d │ %-10d ║",
                cortar(carrera, 30),
                anio,
                inscriptos,
                egresados);
    }

    // Método auxiliar para cortar cadenas largas
    private String cortar(String texto, int maxLength) {
        if (texto == null) return "";
        if (texto.length() <= maxLength) {
            return texto;
        }
        return texto.substring(0, maxLength - 1) + "…";
    }

}
