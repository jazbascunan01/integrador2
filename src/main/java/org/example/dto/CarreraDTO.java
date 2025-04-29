package org.example.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarreraDTO {
    private int id_carrera;
    private String carrera;
    private int duracion;
    public CarreraDTO(String nombre, int cantidadEstudiantes) {
        this.carrera = nombre;
        this.duracion = cantidadEstudiantes; // Reutilizando el campo 'duracion' para almacenar la cantidad de estudiantes
    }
}
