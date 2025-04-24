package org.example.modelo;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
@Entity
public class Estudiante_Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_estudiante_carrera;

    @ManyToOne
    @JoinColumn (name = "dni_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn (name = "id_carrera")
    private Carrera carrera;

    @Column (nullable = false)
    private int anio_inscripcion;

    @Column
    private int anio_graduacion;

    @Column
    private int antiguedad;
}
