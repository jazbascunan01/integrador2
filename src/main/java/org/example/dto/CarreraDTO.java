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
    private int id;
    private String nombre;
    private int duracion;
    public CarreraDTO(String nombre, int cantidadEstudiantes) {
        this.nombre = nombre;
        this.duracion = cantidadEstudiantes; // Reutilizando el campo 'duracion' para almacenar la cantidad de estudiantes
    }
}
