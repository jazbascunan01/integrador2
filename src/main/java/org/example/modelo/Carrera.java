package org.example.modelo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
@Entity
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private int duracion;
    // TODO ver que problema hay con el LAZY
    @OneToMany(mappedBy = "carrera", fetch = FetchType.EAGER)
    private List<Estudiante_Carrera> carreras;

    @Override
    public String toString() {
        return "Carrera{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", duracion=" + duracion +
                ", carreras=" + carreras +
                '}';
    }
}
