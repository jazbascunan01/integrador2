package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarreraConEstudiantesDTO {
    private int idCarrera;
    private String nombreCarrera;
    private long cantidadEstudiantes;

    @Override
    public String toString() {
        return String.format("║ %-34s │ %12d ║", nombreCarrera, cantidadEstudiantes);
    }

}