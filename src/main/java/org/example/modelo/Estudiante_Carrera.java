package main.java.org.example.modelo;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "Estudiante_Carrera")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    public Estudiante_Carrera(Estudiante estudiante, Carrera carrera, int anio_ins, int anio_grad, int antiguedad) {
        this.anio_inscripcion = anio_ins;
        this.anio_graduacion = anio_grad;
        this.antiguedad = antiguedad;
        this.estudiante = estudiante;
        this.carrera = carrera;
    }
}
