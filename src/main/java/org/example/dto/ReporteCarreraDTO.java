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
}
