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
    private int id_carrera;

    @Column(nullable = false)
    private String carrera;

    @Column
    private int duracion;
    @OneToMany(mappedBy = "carrera", fetch = FetchType.LAZY)
    private List<Estudiante_Carrera> carreras;

    @Override
    public String toString() {
        return "Carrera{" +
                "id_carrera=" + id_carrera +
                ", carrera='" + carrera + '\'' +
                ", duracion=" + duracion +
                ", carreras=" + carreras +
                '}';
    }
}
