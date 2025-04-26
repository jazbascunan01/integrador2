package org.example.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO {
    private int dni;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudad;
    private int num_lu;

    @Override
    public String toString() {
        return String.format(
                "║ %-10d │ %-12s │ %-12s │ %-4d │ %-10s │ %-15s │ %-6d ║",
                dni,
                cortar(nombre, 12),
                cortar(apellido, 12),
                edad,
                cortar(genero, 10),
                cortar(ciudad, 15),
                num_lu
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
