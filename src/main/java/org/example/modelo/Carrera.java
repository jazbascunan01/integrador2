package main.java.org.example.modelo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Carrera")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column
    private int duracion;
    @OneToMany(mappedBy = "Carrera")
    private List<Estudiante_Carrera> estudiante_carrera;
    public Carrera(String nombre, int duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.estudiante_carrera = new ArrayList<>();
    }
}
